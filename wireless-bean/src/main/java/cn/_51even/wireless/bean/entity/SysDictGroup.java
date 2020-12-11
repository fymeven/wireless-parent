package cn._51even.wireless.bean.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_sys_dict_group")
public class SysDictGroup implements Serializable {
    @Id
    private String id;

    /**
     * 字典组编码
     */
    @Column(name = "group_code")
    private String groupCode;

    /**
     * 字典组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 字典组描述
     */
    @Column(name = "group_desc")
    private String groupDesc;

    /**
     * 字典组状态（1：启用，2：禁用）
     */
    @Column(name = "group_status")
    private String groupStatus;

    /**
     * 删除标识（1：已删除，0：使用中）
     */
    @Column(name = "delete_flag")
    private String deleteFlag;

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
     * 获取字典组编码
     *
     * @return group_code - 字典组编码
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 设置字典组编码
     *
     * @param groupCode 字典组编码
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * 获取字典组名称
     *
     * @return group_name - 字典组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置字典组名称
     *
     * @param groupName 字典组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取字典组描述
     *
     * @return group_desc - 字典组描述
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     * 设置字典组描述
     *
     * @param groupDesc 字典组描述
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     * 获取字典组状态（1：启用，2：禁用）
     *
     * @return group_status - 字典组状态（1：启用，2：禁用）
     */
    public String getGroupStatus() {
        return groupStatus;
    }

    /**
     * 设置字典组状态（1：启用，2：禁用）
     *
     * @param groupStatus 字典组状态（1：启用，2：禁用）
     */
    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
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
        SysDictGroup other = (SysDictGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupCode() == null ? other.getGroupCode() == null : this.getGroupCode().equals(other.getGroupCode()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getGroupDesc() == null ? other.getGroupDesc() == null : this.getGroupDesc().equals(other.getGroupDesc()))
            && (this.getGroupStatus() == null ? other.getGroupStatus() == null : this.getGroupStatus().equals(other.getGroupStatus()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroupCode() == null) ? 0 : getGroupCode().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getGroupDesc() == null) ? 0 : getGroupDesc().hashCode());
        result = prime * result + ((getGroupStatus() == null) ? 0 : getGroupStatus().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupCode=").append(groupCode);
        sb.append(", groupName=").append(groupName);
        sb.append(", groupDesc=").append(groupDesc);
        sb.append(", groupStatus=").append(groupStatus);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}