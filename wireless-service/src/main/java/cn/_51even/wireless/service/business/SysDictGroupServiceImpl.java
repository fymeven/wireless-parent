package cn._51even.wireless.service.business;

import cn._51even.wireless.core.base.service.BaseService;
import cn._51even.wireless.bean.entity.SysDictGroup;
import cn._51even.wireless.bean.enums.SysDictEnum;
import cn._51even.wireless.bean.enums.SysDictTypeEnum;
import cn._51even.wireless.dal.mapper.SysDictGroupMapper;
import cn._51even.wireless.service.api.SysDictGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

@Service
public class SysDictGroupServiceImpl extends BaseService<SysDictGroup,String> implements SysDictGroupService {

    @Autowired
    private SysDictGroupMapper sysDictGroupMapper;

    @Override
    public List<SysDictGroup> queryDictGroupList(String keyWord) {
        Example example = new Example(SysDictGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteFlag", SysDictEnum.deleteFlag.NO.getCode());
        if (StringUtils.isNotBlank(keyWord)){
            Example.Criteria keyWordCra = example.createCriteria();
            keyWordCra.orLike("groupCode","%"+keyWord+"%");
            keyWordCra.orLike("groupName","%"+keyWord+"%");
            example.and(keyWordCra);
        }
        return select(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        Example example = new Example(SysDictGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(split));
        SysDictGroup sysDictGroup = new SysDictGroup();
        sysDictGroup.setDeleteFlag(SysDictTypeEnum.deleteFlag.YES.getCode());
        return super.updateNotNull(sysDictGroup,example);
    }

    @Override
    public SysDictGroup selectByGroupName(String groupName) {
        if(StringUtils.isBlank(groupName)){
            return null;
        }
        SysDictGroup sysDictGroup = new SysDictGroup();
        sysDictGroup.setGroupName(groupName);
        return sysDictGroupMapper.selectOne(sysDictGroup);
    }
}
