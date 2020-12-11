//package cn._51even.wireless.web;
//
//import com.alibaba.fastjson.JSONObject;
//import cn._51even.wireless.config.ElasticsearchConfig;
//import cn._51even.wireless.core.base.bean.request.BasePageRequest;
//import cn._51even.wireless.core.elasticsearch.ElasticsearchJestHelper;
//import cn._51even.wireless.core.util.HttpUtils;
//import com.github.pagehelper.PageInfo;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class JunitTest {
//
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    String indexName = "ssbk_output_result";
//    String typeName = "ssbk";
//
//    @Resource
//    private ElasticsearchJestHelper elasticsearchJestHelper;
//
//        /**
//     * 创建elasticsearch 索引
//     */
//    @Test
//    public void createElasticsearchIndex(){
//        boolean existsIndex = elasticsearchJestHelper.existsIndex(indexName);
//        if (!existsIndex){
//            boolean createIndex = elasticsearchJestHelper.createIndex(indexName, null, null);
//            logger.debug("创建索引:{}"+ (createIndex ?  "成功" : "失败"),indexName);
//            if (createIndex) {
//                createIndexMapping();
//            }
//        }
//    }
//
//    public void createIndexMapping(){
//        String filePath = "classpath:static/file/elasticsearch_mapping/ssbk_output_result.json";
//        Boolean indexMapping = elasticsearchJestHelper.createIndexMapping(indexName, typeName, filePath);
//        logger.debug("创建 index mapping:{}"+ (indexMapping ?  "成功" : "失败"),indexName);
//    }
//
////    @Test
////    public void clearData(){
////        List<String> idList = elasticsearchJestHelper.queryIdsByQueryBuilder(indexName, typeName, QueryBuilders.matchAllQuery());
////        logger.info("---idList sieze:{}---",idList);
////        if (!idList.isEmpty()){
////            elasticsearchJestHelper.bulkDeleteByIds(indexName, typeName, idList);
////        }
////    }
//
////    @Test
////    public void importData(){
////        List<SpbkCameraInfo> spbkCameraInfos = spbkCameraInfoMapper.selectAll();
////        for (SpbkCameraInfo spbkCameraInfo : spbkCameraInfos) {
////            JSONObject jsonObject = new JSONObject();
////            String id = spbkCameraInfo.getId();
////            jsonObject.put("id", id);
////            jsonObject.put("cameraName",spbkCameraInfo.getCameraName());
////            jsonObject.put("cameraCode",spbkCameraInfo.getCameraCode());
////            JSONObject location = new JSONObject(true);
////            location.put("lat",spbkCameraInfo.getLat());
////            location.put("lon",spbkCameraInfo.getLon());
////            jsonObject.put("location",location);
////            jsonObject.put("cameraType",spbkCameraInfo.getCameraType());
////            jsonObject.put("areaCode",spbkCameraInfo.getAreaCode());
////            jsonObject.put("cameraIp",spbkCameraInfo.getCameraIp());
////            elasticsearchRestHelper.insertDocument(indexName, typeName,id,jsonObject);
////        }
////    }
//
////    @Test
////    public void updateData(){
////        List<SpbkCameraInfo> spbkCameraInfos = spbkCameraInfoMapper.selectAll();
////        for (SpbkCameraInfo spbkCameraInfo : spbkCameraInfos) {
////            JSONObject jsonObject = new JSONObject();
////            String id = spbkCameraInfo.getId();
////            jsonObject.put("id", id);
////            jsonObject.put("cameraName",spbkCameraInfo.getCameraName());
////            jsonObject.put("cameraCode",spbkCameraInfo.getCameraCode());
////            JSONObject location = new JSONObject(true);
////            location.put("lat",spbkCameraInfo.getLat());
////            location.put("lon",spbkCameraInfo.getLon());
////            jsonObject.put("location",location);
////            jsonObject.put("cameraType",spbkCameraInfo.getCameraType());
////            jsonObject.put("areaCode",spbkCameraInfo.getAreaCode());
////            jsonObject.put("cameraIp",spbkCameraInfo.getCameraIp());
////            elasticsearchRestHelper.updateDocument(indexName, typeName,id,jsonObject);
////        }
////    }
//
//    @Test
//    public void test(){
//        JSONObject header = new JSONObject();
//        header.put("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImV2ZW4iLCJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTU3ODg0MDEyNiwibmJmIjoxNTc4NzUzNzI2fQ.4AlKPp0UFRMgQ8J521lfEyfqjoJ1bn8voBCr4u2Faks");
//        JSONObject param = new JSONObject();
//        param.put("areaType",2);
//        JSONObject circle = new JSONObject();
//        circle.put("radius","100");
//        JSONObject location = new JSONObject();
//        location.put("lat","39.87263");
//        location.put("lon","116.29389");
//        circle.put("location",location);
////        param.put("circle","{\"radius\":\"100\",\"location\":{\"lat\":\"39.87263\",\"lon\":\"116.29389\"}}");
//        param.put("circle",JSONObject.toJSONString(circle));
//        JSONObject jsonObject = HttpUtils.doPost("http://172.16.100.136:8811/camera/selectCameraByArea", param, header, JSONObject.class);
//        System.out.println(jsonObject);
//    }
//
//    @Resource
//    private ElasticsearchService elasticsearchService;
//
//    @Resource
//    private ElasticsearchConfig elasticsearchConfig;
//
//    @Test
//    public void search(){
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("createUserId.keyword","6EA3DDC5E3470090C08D871FD829A23F"));
//        queryBuilder.must(QueryBuilders.termQuery("bkType","person"));
//        queryBuilder.must(QueryBuilders.termQuery("businessType","1"));
//        queryBuilder.must(QueryBuilders.termQuery("sjyCode","tldp"));
//        queryBuilder.must(QueryBuilders.termQuery("taskId","task_id_3"));
//        queryBuilder.must(QueryBuilders.rangeQuery("yjTime").format("yyyy-MM-dd HH:mm:ss").gte("2020-01-01 00:00:00"));
//        queryBuilder.must(QueryBuilders.rangeQuery("yjTime").format("yyyy-MM-dd HH:mm:ss").lte("2020-08-01 00:00:00"));
////        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
//        BasePageRequest pageRequest = new BasePageRequest();
//        pageRequest.setSortColumn("yjTime");
//        pageRequest.setDoPage(false);
//        PageInfo<JSONObject> pageInfo = elasticsearchJestHelper.queryPage(indexName, typeName, queryBuilder, pageRequest, null);
//        logger.debug("pageInfo:{}",JSONObject.toJSONString(pageInfo));
//    }
//
//    @Test
//    public void selectCount(){
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("createUserId.keyword","6EA3DDC5E3470090C08D871FD829A23F"));
//        queryBuilder.must(QueryBuilders.termQuery("bkType","person"));
//        queryBuilder.must(QueryBuilders.termQuery("businessType","1"));
//        queryBuilder.must(QueryBuilders.termQuery("sjyCode","tldp"));
//        queryBuilder.must(QueryBuilders.termQuery("taskId","task_id_3"));
//        queryBuilder.must(QueryBuilders.rangeQuery("yjTime").format("yyyy-MM-dd HH:mm:ss").gte("2020-01-01 00:00:00"));
//        queryBuilder.must(QueryBuilders.rangeQuery("yjTime").format("yyyy-MM-dd HH:mm:ss").lte("2020-08-01 00:00:00"));
//        Long count = elasticsearchJestHelper.selectCount(indexName, typeName, queryBuilder);
//        logger.debug("count:{}",count);
//    }
//
//    @Test
//    public void deleteByQuery(){
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("sjyCode","oceanum"));
//        boolean result = elasticsearchJestHelper.deleteByQuery(indexName, typeName, queryBuilder);
//        logger.debug("result:{}",result);
//    }
//
//    @Test
//    public void queryEs(){
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.wildcardQuery("xzqh", "*33010*"));
//        List<JSONObject> list = elasticsearchJestHelper.queryAll(indexName, typeName, queryBuilder);
//        logger.info("SIZE:{}",list.size());
//    }
//
//    @Test
//    public void doPost(){
//        JSONObject param =new JSONObject();
//        param.put("pageNum",1);
//        param.put("pageSize",20);
//        JSONObject httpResult = HttpUtils.doPost("http://www.baidu.com", param, HttpUtils.ContentType.application_json, JSONObject.class);
//    }
//}
