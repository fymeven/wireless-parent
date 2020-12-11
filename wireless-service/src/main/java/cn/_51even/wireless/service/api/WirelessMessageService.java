package cn._51even.wireless.service.api;

import cn._51even.wireless.bean.entity.WirelessMessage;
import cn._51even.wireless.bean.request.wirelessMessage.PageWirelessMessageReq;
import cn._51even.wireless.core.base.bean.response.ResponseResult;

public interface WirelessMessageService {
    ResponseResult pageList(PageWirelessMessageReq pageWirelessMessageReq);

    boolean save(WirelessMessage wirelessMessage);
}
