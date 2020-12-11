package cn._51even.wireless.bean.request.sysDict;

import cn._51even.wireless.core.base.bean.request.BaseRequest;

public class InsertSysDictReq extends BaseRequest {

    private String dictName;

    private String dictCode;

    private String dictGroup;

    private String parentCode;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictGroup() {
        return dictGroup;
    }

    public void setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
