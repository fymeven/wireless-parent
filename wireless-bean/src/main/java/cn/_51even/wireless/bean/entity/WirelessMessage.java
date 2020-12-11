package cn._51even.wireless.bean.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "wireless_message")
public class WirelessMessage implements Serializable {
    @Id
    private String id;

    @Column(name = "remote_ip")
    private String remoteIp;

    @Column(name = "remote_port")
    private Integer remotePort;

    @Column(name = "receive_data")
    private String receiveData;

    @Column(name = "receive_time")
    private Date receiveTime;

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
     * @return remote_ip
     */
    public String getRemoteIp() {
        return remoteIp;
    }

    /**
     * @param remoteIp
     */
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    /**
     * @return remote_port
     */
    public Integer getRemotePort() {
        return remotePort;
    }

    /**
     * @param remotePort
     */
    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    /**
     * @return receive_data
     */
    public String getReceiveData() {
        return receiveData;
    }

    /**
     * @param receiveData
     */
    public void setReceiveData(String receiveData) {
        this.receiveData = receiveData;
    }

    /**
     * @return receive_time
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * @param receiveTime
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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
        WirelessMessage other = (WirelessMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemoteIp() == null ? other.getRemoteIp() == null : this.getRemoteIp().equals(other.getRemoteIp()))
            && (this.getRemotePort() == null ? other.getRemotePort() == null : this.getRemotePort().equals(other.getRemotePort()))
            && (this.getReceiveData() == null ? other.getReceiveData() == null : this.getReceiveData().equals(other.getReceiveData()))
            && (this.getReceiveTime() == null ? other.getReceiveTime() == null : this.getReceiveTime().equals(other.getReceiveTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemoteIp() == null) ? 0 : getRemoteIp().hashCode());
        result = prime * result + ((getRemotePort() == null) ? 0 : getRemotePort().hashCode());
        result = prime * result + ((getReceiveData() == null) ? 0 : getReceiveData().hashCode());
        result = prime * result + ((getReceiveTime() == null) ? 0 : getReceiveTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", remoteIp=").append(remoteIp);
        sb.append(", remotePort=").append(remotePort);
        sb.append(", receiveData=").append(receiveData);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}