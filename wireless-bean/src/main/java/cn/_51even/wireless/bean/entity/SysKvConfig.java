package cn._51even.wireless.bean.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_sys_kv_config")
public class SysKvConfig implements Serializable {
    /**
     * key
     */
    @Id
    @Column(name = "config_key")
    private String configKey;

    /**
     * value
     */
    @Column(name = "config_value")
    private String configValue;

    /**
     * mark
     */
    @Column(name = "config_mark")
    private String configMark;

    private static final long serialVersionUID = 1L;

    /**
     * 获取key
     *
     * @return config_key - key
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * 设置key
     *
     * @param configKey key
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    /**
     * 获取value
     *
     * @return config_value - value
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * 设置value
     *
     * @param configValue value
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    /**
     * 获取mark
     *
     * @return config_mark - mark
     */
    public String getConfigMark() {
        return configMark;
    }

    /**
     * 设置mark
     *
     * @param configMark mark
     */
    public void setConfigMark(String configMark) {
        this.configMark = configMark;
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
        SysKvConfig other = (SysKvConfig) that;
        return (this.getConfigKey() == null ? other.getConfigKey() == null : this.getConfigKey().equals(other.getConfigKey()))
            && (this.getConfigValue() == null ? other.getConfigValue() == null : this.getConfigValue().equals(other.getConfigValue()))
            && (this.getConfigMark() == null ? other.getConfigMark() == null : this.getConfigMark().equals(other.getConfigMark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConfigKey() == null) ? 0 : getConfigKey().hashCode());
        result = prime * result + ((getConfigValue() == null) ? 0 : getConfigValue().hashCode());
        result = prime * result + ((getConfigMark() == null) ? 0 : getConfigMark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configKey=").append(configKey);
        sb.append(", configValue=").append(configValue);
        sb.append(", configMark=").append(configMark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}