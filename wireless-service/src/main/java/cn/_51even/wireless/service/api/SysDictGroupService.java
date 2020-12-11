package cn._51even.wireless.service.api;

import cn._51even.wireless.bean.entity.SysDictGroup;

import java.util.List;

public interface SysDictGroupService {

    boolean save(SysDictGroup sysDictType);

    List<SysDictGroup> queryDictGroupList(String keyWord);

    boolean updateNotNull(SysDictGroup sysDictGroup);

    boolean deleteBatch(String ids);

    /**
     * 通过groupName精确匹配出SysDictGroup
     * @param groupName
     * @return
     */
    SysDictGroup selectByGroupName(String groupName);
}
