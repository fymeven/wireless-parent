package cn._51even.wireless.bean.request.wirelessMessage;

import cn._51even.wireless.core.base.bean.request.BasePageRequest;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PageWirelessMessageReq extends BasePageRequest {

    @ApiModelProperty(value = "设备IP",name = "remoteIp",required = false)
    private String remoteIp;

    @ApiModelProperty(value = "设备端口",name = "remotePort",required = false)
    private String remotePort;

    @ApiModelProperty(value = "接收内容",name = "receiveData",required = false)
    private String receiveData;

    @ApiModelProperty(value = "开始时间（格式：yyyy-MM-dd HH:mm:ss）",name = "receiveTimeStart",required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTimeStart;

    @ApiModelProperty(value = "结束时间（格式：yyyy-MM-dd HH:mm:ss）",name = "receiveTimeEnd",required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTimeEnd;

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public String getReceiveData() {
        return receiveData;
    }

    public void setReceiveData(String receiveData) {
        this.receiveData = receiveData;
    }

    public Date getReceiveTimeStart() {
        return receiveTimeStart;
    }

    public void setReceiveTimeStart(Date receiveTimeStart) {
        this.receiveTimeStart = receiveTimeStart;
    }

    public Date getReceiveTimeEnd() {
        return receiveTimeEnd;
    }

    public void setReceiveTimeEnd(Date receiveTimeEnd) {
        this.receiveTimeEnd = receiveTimeEnd;
    }
}
