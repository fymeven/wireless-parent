package cn._51even.wireless.core.elasticsearch;

import cn._51even.wireless.core.base.bean.request.BasePageRequest;
import cn._51even.wireless.core.base.exception.BusinessException;
import cn._51even.wireless.core.util.PageHelperUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.core.*;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import io.searchbox.params.SearchType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

@ConditionalOnClass(name = "io.searchbox.client.JestClient")
@Component
@AutoConfigureAfter(name = "jestClient")
public class ElasticsearchJestHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    @Autowired(required = false)
    private JestClient jestClient;

    /**
     * 创建 index 索引
     * @param indexName
     * @param shards
     * @param replicas
     * @return
     */
    public boolean createIndex(String indexName,Integer shards,Integer replicas) {
        try {
            Map settings = new HashMap();
            if (shards == null){
                shards = 3;
            }
            if (replicas == null){
                replicas = 3;
            }
            settings.put("number_of_shards", shards);
            settings.put("number_of_replicas", replicas);
            JestResult result = jestClient.execute(new CreateIndex.Builder(indexName).settings(settings).build());
            logger.debug("createIndex:{}",result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("createIndex error, cause: {}", e);
            return false;
        }
    }

    /**
     * 判断 index 索引是否存在
     * @param indexName
     * @return
     */
    public boolean existsIndex(String indexName) {
        try {
            JestResult result = jestClient.execute(new IndicesExists.Builder(indexName).build());
            logger.debug("existsIndex:{}",result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("existsIndex error, cause: {}", e);
            return false;
        }
    }

    /**
     * 查看 index 索引健康信息
     * @param indexName
     * @return
     */
    public String health(String indexName) {
        try {
            JestResult result = jestClient.execute(new Health.Builder().addIndex(indexName).build());
            return result.getJsonString();
        } catch (Exception e) {
            logger.error("health error, cause: {}", e);
            return null;
        }
    }

    /**
     * 设置 index 索引别名
     * @param indexName
     * @param aliasName
     * @return
     */
    public Boolean addAlias(String indexName,String aliasName) {
        try {
            JestResult result = jestClient.execute(new ModifyAliases.Builder(new AddAliasMapping.Builder(indexName, aliasName).build()).build());
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("addAlias error, cause: {}", e);
            return false;
        }
    }

    /**
     * 移除 index 索引别名
     * @param indexName
     * @param aliasName
     * @return
     */
    public Boolean removeAlias(String indexName,String aliasName) {
        try {
            JestResult result = jestClient.execute(new ModifyAliases.Builder(new RemoveAliasMapping.Builder(indexName, aliasName).build()).build());
            logger.debug("removeAlias:{}",result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("addAlias error, cause: {}", e);
            return false;
        }
    }

    /**
     * 加载文件内容
     * @param filePath
     * @return
     */
    public String loadText(String filePath) {
        String textData = null;
        try {
            File file = ResourceUtils.getFile(filePath);
            textData =  IOUtils.toString(FileUtils.openInputStream(file), Charset.forName("UTF-8"));
        } catch(Exception e) {
            logger.error("loadText error:{}", e);
        }
        return textData;
    }

    /**
     * 创建 mapping 映射
     * @param indexName
     * @param typeName
     * @param filePath
     * @return
     */
    public Boolean createIndexMapping(String indexName,String typeName,String filePath) {
        try {
            String source = loadText(filePath);
            PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
            JestResult result = jestClient.execute(putMapping);
            return result.isSucceeded();
        } catch(Exception e) {
            logger.error("createIndexMapping error:{}", e);
            return false;
        }
    }

    public PageInfo<JSONObject> queryPage(String indexName, String typeName, QueryBuilder queryBuilder, BasePageRequest pageRequest, Set<String> highlightFieldSet){
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //超出ES最大数据限制，则返回最后一页
            int maxResultWindow = elasticsearchProperties.getMaxResultWindow();
            if (pageRequest.getOffsetNum()+pageRequest.getPageSize() > maxResultWindow){
                BigDecimal totalDecimal = BigDecimal.valueOf(maxResultWindow);
                BigDecimal pageSizeDecimal = BigDecimal.valueOf((long) pageRequest.getPageSize());
                int pages = totalDecimal.divide(pageSizeDecimal, BigDecimal.ROUND_FLOOR).intValue();
                pageRequest.setPageNum(pages);
            }
            //条件查询
            searchSourceBuilder.query(queryBuilder);
            //分页
            if (pageRequest.getDoPage()){
                searchSourceBuilder.from(pageRequest.getOffsetNum());
                searchSourceBuilder.size(pageRequest.getPageSize());
            }
//            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //排序
            if (pageRequest.getDoSort()){
                if (StringUtils.isBlank(pageRequest.getSortColumn())){
                    throw new BusinessException("当前方法已启用排序,但未指定排序字段[sortColumn]");
                }
                searchSourceBuilder.sort(pageRequest.getSortColumn(), SortOrder.fromString(pageRequest.getSortRule()));
            }
            //设置高亮显示字段
            if (highlightFieldSet !=null && !highlightFieldSet.isEmpty()){
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                for (String highlightField : highlightFieldSet) {
                    HighlightBuilder.Field field = new HighlightBuilder.Field(highlightField)
//                            .preTags("<em class=\"highlight\" style=\"color:red;\">").postTags("</em>")
                            ; // 高亮字段
                    highlightBuilder.field(field);  // 添加到 builder
                    highlightBuilder.requireFieldMatch(false);//只有包含查询匹配的字段才会突出显示
                }
                searchSourceBuilder.highlighter(highlightBuilder);
            }
            logger.debug("searchSourceBuilder:{}", searchSourceBuilder);

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(typeName).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .build();


            SearchResult result = jestClient.execute(search);
            logger.debug("查询结果:{}", result.getJsonString());
            Long totalHits = result.getTotal();
            List<JSONObject> list = new ArrayList<>();
            result.getHits(JSONObject.class).forEach(item -> {
                list.add(item.source);
            });
//            for (SearchHit hit : hits) {
//                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(sourceAsMap);
//                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//                if (highlightFields != null){
//                    List highlightList = new ArrayList();
//                    for (String highlightKey : highlightFields.keySet()) {
//                        if (jsonObject.containsKey(highlightKey)){
//                            JSONObject highlightField = new JSONObject();
//                            highlightField.put(highlightKey,highlightFields.get(highlightKey).getFragments()[0].toString());
//                            highlightList.add(highlightField);
//                        }
//                    }
//                    jsonObject.put("highlightField",highlightList);
//                }
//                list.add(jsonObject);
//            }
            PageInfo<JSONObject> pageInfo = PageHelperUtil.buildPageInfo(list, totalHits, pageRequest.getPageNum(), pageRequest.getPageSize());
            return pageInfo;
        } catch (IOException e) {
            logger.error("queryPage error,cause:{}",e);
            return null;
        }
    }

    /**
     * 使用游标循环查询全部数据
     * @param indexName
     * @param typeName
     * @param queryBuilder
     * @return
     */
    public List<JSONObject> queryAll(String indexName, String typeName, QueryBuilder queryBuilder) {
        Long beginTime = new Date().getTime();
        List<JSONObject> list = new ArrayList<>();
        try {
            int maxResultWindow = elasticsearchProperties.getMaxResultWindow();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(maxResultWindow);
            searchSourceBuilder.query(queryBuilder);
            Search search = new Search
                    .Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(typeName)
                    .setParameter(Parameters.SCROLL, "10m")
                    .build();
            JestResult result = jestClient.execute(search);
            while(result.isSucceeded()
                    && result.getSourceAsObjectList(Map.class).size() > 0) {
                String scrollId = result.getJsonObject().get("_scroll_id").getAsString();
                SearchScroll scroll = new SearchScroll.Builder(scrollId, "10m").build();
                result = jestClient.execute(scroll);
                list.addAll(result.getSourceAsObjectList(JSONObject.class));
            }
        } catch (Exception e){
            logger.error("queryAll error,cause:{}",e);
        }
        Long runTime =new Date().getTime()-beginTime;
        logger.debug("查询时间:{}",runTime);
        return list;
    }

    public Long selectCount(String indexName, String typeName, QueryBuilder queryBuilder){
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //不需要 source
            searchSourceBuilder.fetchSource(false);
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(typeName).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .build();
            SearchResult result = jestClient.execute(search);
            logger.debug("searchSourceBuilder:{}", searchSourceBuilder);
            return result.getTotal();
        } catch (Exception e){
            logger.error("count error,cause:{}",e);
            return 0L;
        }
    }

    /**
     * 聚合查询-返回统计数值
     *
     * @param indexName 索引
     * @param typeName 文档类型
     * @param aggName 聚合名称
     * @param queryBuilder 查询条件
     * @param aggregationBuilder 聚合条件
     * @return 统计值
     */
    public Long selectCount(String indexName, String typeName, String aggName, QueryBuilder queryBuilder, AggregationBuilder aggregationBuilder) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(0);
            searchSourceBuilder.query(queryBuilder);
            // 聚合条件
            searchSourceBuilder.aggregation(aggregationBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchSourceBuilder.fetchSource(false);

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(typeName)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .build();
            SearchResult result = jestClient.execute(search);
            MetricAggregation aggregations = result.getAggregations();
            // 根据聚合名称获取统计值
            return aggregations.getValueCountAggregation(aggName).getValueCount();
        } catch (Exception e) {
            logger.error("select distinct count error, cause: {0}", e);
            return 0L;
        }
    }

    /**
     * 聚合查询-返回分类统计的值
     *
     * @param indexName 索引
     * @param typeName 文档类型
     * @param aggregationBuilder 聚合条件
     * @return 分类统计结果
     */
    public SearchResult selectCount(String indexName, String typeName, AggregationBuilder aggregationBuilder) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(0);
            searchSourceBuilder.aggregation(aggregationBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchSourceBuilder.fetchSource(false);

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indexName)
                    .addType(typeName)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .build();
            return jestClient.execute(search);
        } catch (Exception e) {
            logger.error("select aggregate count error, cause: {0}", e);
            return null;
        }
    }

    public boolean deleteByQuery(String indexName, String typeName, QueryBuilder queryBuilder) {
        try {
            String query = new SearchSourceBuilder().query(queryBuilder).toString();
            JestResult result = jestClient.execute(new DeleteByQuery.Builder(query).addIndex(indexName).addType(typeName).build());
            if (!result.isSucceeded()) {
                logger.error(">>> deleteByQuery error:{}", result.getErrorMessage());
            }
            logger.debug("deleteByQuery:{}", result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("count error,cause:{}", e);
            return false;
        }
    }

    public boolean putData(String indexName,String typeName,String idColumn,JSONObject dataObj) {
        try {
            Index put = new Index.Builder(dataObj).index(indexName).type(typeName).id(dataObj.getString(idColumn)).build();
            DocumentResult result = jestClient.execute(put);
            logger.debug("putData:{}",result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("count error,cause:{}", e);
            return false;
        }
    }

    /**
     * bulk 批量插入数据
     * @param indexName
     * @param typeName
     * @param idColumn
     * @param dataList
     * @return
     */
    public boolean putDataBulk(String indexName,String typeName,String idColumn,List<JSONObject> dataList) {
        try {
            List<Index> list = new ArrayList<>();
            for (JSONObject jsonObject : dataList) {
                Index index = new Index.Builder(jsonObject).id(jsonObject.getString(idColumn)).build();
                list.add(index);
            }
            Bulk bulk = new Bulk.Builder()
                    .defaultIndex(indexName)
                    .defaultType(typeName)
                    .addAction(list).build();
            BulkResult result = jestClient.execute(bulk);
            logger.debug("putDataBulk:{}",result);
            return result.isSucceeded();
        } catch (Exception e) {
            logger.error("count error,cause:{}", e);
            return false;
        }
    }

}
