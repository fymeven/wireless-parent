package cn._51even.wireless.model;

import cn._51even.wireless.utils.WirelessDataHandler;

public class AttrTLV {

    /**
     * Attr属性字段类型枚举
     */
    private WirelessDataHandler.Attr AttrType;

    /**
     * Attr属性字段总长度
     */
    private Integer AttrLen;

    /**
     * Attr属性字段值
     */
    private String AttrValue;

    public WirelessDataHandler.Attr getAttrType() {
        return AttrType;
    }

    public void setAttrType(WirelessDataHandler.Attr attrType) {
        AttrType = attrType;
    }

    public Integer getAttrLen() {
        return AttrLen;
    }

    public void setAttrLen(Integer attrLen) {
        AttrLen = attrLen;
    }

    public String getAttrValue() {
        return AttrValue;
    }

    public void setAttrValue(String attrValue) {
        AttrValue = attrValue;
    }
}
