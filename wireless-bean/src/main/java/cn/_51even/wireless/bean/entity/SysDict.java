package cn._51even.wireless.bean.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_sys_dict")
public class SysDict implements Serializable {
    @Id
    private String id;

    /**
     * 字典名称
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 字典值
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典组编码
     */
    @Column(name = "dict_group")
    private String dictGroup;

    /**
     * 上级字典值
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 字典状态（1：启用，2：禁用）
     */
    @Column(name = "dict_status")
    private String dictStatus;

    /**
     * 删除标识（1：已删除，0：使用中）
     */
    @Column(name = "delete_flag")
    private String deleteFlag;

    /**
     * 排序号(从大到小正序)
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取字典名称
     *
     * @return dict_name - 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置字典名称
     *
     * @param dictName 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取字典值
     *
     * @return dict_code - 字典值
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 设置字典值
     *
     * @param dictCode 字典值
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取字典组编码
     *
     * @return dict_group - 字典组编码
     */
    public String getDictGroup() {
        return dictGroup;
    }

    /**
     * 设置字典组编码
     *
     * @param dictGroup 字典组编码
     */
    public void setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
    }

    /**
     * 获取上级字典值
     *
     * @return parent_code - 上级字典值
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置上级字典值
     *
     * @param parentCode 上级字典值
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 获取字典状态（1：启用，2：禁用）
     *
     * @return dict_status - 字典状态（1：启用，2：禁用）
     */
    public String getDictStatus() {
        return dictStatus;
    }

    /**
     * 设置字典状态（1：启用，2：禁用）
     *
     * @param dictStatus 字典状态（1：启用，2：禁用）
     */
    public void setDictStatus(String dictStatus) {
        this.dictStatus = dictStatus;
    }

    /**
     * 获取删除标识（1：已删除，0：使用中）
     *
     * @return delete_flag - 删除标识（1：已删除，0：使用中）
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标识（1：已删除，0：使用中）
     *
     * @param deleteFlag 删除标识（1：已删除，0：使用中）
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取排序号(从大到小正序)
     *
     * @return sort_no - 排序号(从大到小正序)
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序号(从大到小正序)
     *
     * @param sortNo 排序号(从大到小正序)
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysDict other = (SysDict) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDictName() == null ? other.getDictName() == null : this.getDictName().equals(other.getDictName()))
            && (this.getDictCode() == null ? other.getDictCode() == null : this.getDictCode().equals(other.getDictCode()))
            && (this.getDictGroup() == null ? other.getDictGroup() == null : this.getDictGroup().equals(other.getDictGroup()))
            && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
            && (this.getDictStatus() == null ? other.getDictStatus() == null : this.getDictStatus().equals(other.getDictStatus()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDictName() == null) ? 0 : getDictName().hashCode());
        result = prime * result + ((getDictCode() == null) ? 0 : getDictCode().hashCode());
        result = prime * result + ((getDictGroup() == null) ? 0 : getDictGroup().hashCode());
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getDictStatus() == null) ? 0 : getDictStatus().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dictName=").append(dictName);
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictGroup=").append(dictGroup);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", dictStatus=").append(dictStatus);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}