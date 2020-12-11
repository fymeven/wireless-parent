package cn._51even.wireless.bean.request.sysDictGroup;

import cn._51even.wireless.core.base.bean.request.BaseRequest;

public class InsertSysDictGroupReq extends BaseRequest {

    private String groupCode;

    private String groupName;

    private String groupDesc;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
}
