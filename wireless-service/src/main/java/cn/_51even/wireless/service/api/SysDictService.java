package cn._51even.wireless.service.api;

import cn._51even.wireless.bean.request.sysDict.PageSysDictReq;
import cn._51even.wireless.bean.entity.SysDict;
import cn._51even.wireless.core.base.bean.response.ResponseResult;

import java.util.List;

public interface SysDictService {

    ResponseResult pageDictList(PageSysDictReq pageSysDictReq);

    boolean save(SysDict sysDict);

    String getDictNameByCode(String dictGroup,String dictCode);

    String getCodeByDictName(String dictGroup,String name);

    /**
     * 通过groupCode查询SysDict集合
     * @param groupCode
     * @return
     */
    List<SysDict> selectByDictGroup(String groupCode);

    /**
     * 查询布控类型集合
     * @return
     */
    ResponseResult selectBkType();
}
