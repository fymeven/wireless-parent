package cn._51even.wireless.core.base.service;

import cn._51even.wireless.core.base.bean.request.BasePageRequest;
import cn._51even.wireless.core.base.dal.BaseMapper;
import cn._51even.wireless.core.base.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.github.pagehelper.page.PageMethod.orderBy;
import static com.github.pagehelper.page.PageMethod.startPage;

public abstract class BaseService<Entity, ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected BaseMapper<Entity> mapper;

    public boolean save(Entity entity){
        int i = mapper.insertSelective(entity);
        return i > 0;
    }

    public boolean saveList(List<Entity> list){
        int i = mapper.insertMysqlAndOthersList(list);
        return i > 0;
    }

    public boolean updateIncludeNull(Entity entity){
        int i = mapper.updateByPrimaryKey(entity);
        return i > 0;
    }

    public boolean updateNotNull(Entity entity){
        int i = mapper.updateByPrimaryKeySelective(entity);
        return i > 0;
    }

    public boolean updateIncludeNull(Entity entity,Example example){
        int i = mapper.updateByExample(entity,example);
        return i > 0;
    }

    public boolean updateNotNull(Entity entity,Example example){
        int i = mapper.updateByExampleSelective(entity,example);
        return i > 0;
    }

    public boolean delete(ID id){
        int i = mapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    public boolean delete(Example example){
        int i = mapper.deleteByExample(example);
        return i > 0;
    }

    public PageInfo<Entity> selectPage(Integer pageNum,Integer pageSize,Entity entity){
        startPage(pageNum,pageSize);
        List<Entity> list = select(entity);
        return new PageInfo<>(list);
    }

    public PageInfo<Entity> selectPage(Integer pageNum,Integer pageSize,Example example){
        startPage(pageNum,pageSize);
        List<Entity> list = select(example);
        return new PageInfo<>(list);
    }

    public PageInfo<Entity> selectPage(Integer pageNum,Integer pageSize,Condition condition){
        startPage(pageNum,pageSize);
        List<Entity> list = select(condition);
        return new PageInfo<>(list);
    }

    /**
     * 设置排序和分页信息
     *
     * @param params 继承了 {@link BasePageRequest} 的分页条件实体
     * @param defaultSortColumn  默认排序字段
     * @param alias 表别名
     */
    protected void beforeQuery(BasePageRequest params, String defaultSortColumn, String alias) {
        if (params.getDoSort().equals(Boolean.TRUE)) {
            if (StringUtils.isBlank(params.getSortColumn())) {
                params.setSortColumn(defaultSortColumn);
            }
            if (StringUtils.isBlank(params.getSortRule())) {
                params.setSortRule("DESC");
            }
            orderBy(" " + alias + "." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, params.getSortColumn()) + " " + params.getSortRule());
        } else {
            orderBy(" " + alias + "." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, params.getSortColumn()) + " DESC");
        }
        if (params.getDoPage().equals(Boolean.TRUE)) {
            startPage(params.getPageNum(), params.getPageSize());
        }
    }

    /**
     * 获取一个Example实例
     */
    protected Example newExample() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class<?> clazz = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
            return new Example(clazz);
        }
        throw new BusinessException("Example 实例化错误");
    }

    public Entity selectById(ID id){
        return mapper.selectByPrimaryKey(id);
    }

    public Entity selectOne(Entity entity){
        return mapper.selectOne(entity);
    }

    public Entity selectOne(Example example){
        return mapper.selectOneByExample(example);
    }

    public List<Entity> select(Entity entity){
        return mapper.select(entity);
    }

    public List<Entity> select(Example example){
        return mapper.selectByExample(example);
    }

    public List<Entity> select(Condition condition){
        return mapper.selectByCondition(condition);
    }

    public List<Entity> selectAll(){
        return mapper.selectAll();
    }

    public int count(Entity entity){
        return mapper.selectCount(entity);
    }

    public int count(Example example){
        return mapper.selectCountByExample(example);
    }

    public int count(Condition condition){
        return mapper.selectCountByCondition(condition);
    }
}
