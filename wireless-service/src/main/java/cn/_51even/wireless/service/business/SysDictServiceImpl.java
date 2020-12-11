package cn._51even.wireless.service.business;

import cn._51even.wireless.bean.request.sysDict.PageSysDictReq;
import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.core.base.service.BaseService;
import cn._51even.wireless.bean.entity.SysDict;
import cn._51even.wireless.bean.enums.SysDictEnum;
import cn._51even.wireless.dal.mapper.SysDictMapper;
import cn._51even.wireless.service.api.SysDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SysDictServiceImpl extends BaseService<SysDict,String> implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public ResponseResult pageDictList(PageSysDictReq pageSysDictReq) {
        Example example = new Example(SysDict.class);
        example.orderBy("sortNo").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteFlag", SysDictEnum.deleteFlag.NO.getCode());
        if (StringUtils.isNotBlank(pageSysDictReq.getDictGroup())){
            criteria.andEqualTo("dictGroup", pageSysDictReq.getDictGroup());
        }
        if (StringUtils.isNotBlank(pageSysDictReq.getDictName())){
            criteria.andEqualTo("dictName", pageSysDictReq.getDictName());
        }
        if (StringUtils.isNotBlank(pageSysDictReq.getDictCode())){
            criteria.andEqualTo("dictCode", pageSysDictReq.getDictCode());
        }
        if (StringUtils.isNotBlank(pageSysDictReq.getDictStatus())){
            criteria.andEqualTo("dictStatus", pageSysDictReq.getDictStatus());
        }
        if (StringUtils.isNotBlank(pageSysDictReq.getParentCode())){
            criteria.andEqualTo("parentCode", pageSysDictReq.getParentCode());
        }else {
            criteria.andIsNull("parentCode");
        }
        if (pageSysDictReq.getDoSort()){
            if (StringUtils.isBlank(pageSysDictReq.getSortColumn())){
                pageSysDictReq.setSortColumn("id DESC");
            }
            PageHelper.orderBy(pageSysDictReq.getSortColumn());
        }
        if (pageSysDictReq.getDoPage()){
            PageHelper.startPage(pageSysDictReq.getPageNum(), pageSysDictReq.getPageSize());
        }
        List<SysDict> list = select(example);
        if (pageSysDictReq.getDoPage()){
            PageInfo pageInfo = new PageInfo<>(list);
            return ResponseResult.successData(pageInfo);
        }else{
            return ResponseResult.successData(list);
        }
    }

    @Override
    public String getDictNameByCode(String dictGroup,String dictCode) {
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dictGroup",dictGroup);
        criteria.andEqualTo("dictCode",dictCode);
        example.selectProperties("dictName");
        SysDict sysDict = selectOne(example);
        return sysDict != null ? sysDict.getDictName() : null;
    }

    @Override
    public String getCodeByDictName(String dictGroup, String dictName) {
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dictGroup",dictGroup);
        criteria.andEqualTo("dictName",dictName);
        example.selectProperties("dictCode");
        SysDict sysDict = selectOne(example);
        return sysDict != null ? sysDict.getDictCode() : null;
    }

    @Override
    public List<SysDict> selectByDictGroup(String groupCode) {
        if(StringUtils.isBlank(groupCode)){
            return Collections.emptyList();
        }
        Condition condition = new Condition(SysDict.class);
        condition.createCriteria().andEqualTo("dictGroup",groupCode);
        return sysDictMapper.selectByCondition(condition);
    }

    @Override
    public ResponseResult selectBkType() {
        Condition condition = new Condition(SysDict.class);
        condition.orderBy("sortNo").asc();
        condition.createCriteria().andEqualTo("dictGroup","bkType").andIsNull("parentCode");
        List<SysDict> sysDictList = sysDictMapper.selectByCondition(condition);
        return ResponseResult.successData(sysDictList);
    }
}
