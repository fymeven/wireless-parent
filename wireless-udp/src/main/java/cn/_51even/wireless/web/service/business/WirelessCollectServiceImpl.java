package cn._51even.wireless.web.service.business;

import cn._51even.wireless.bean.entity.WirelessMessage;
import cn._51even.wireless.core.util.UUIDUtil;
import cn._51even.wireless.service.api.WirelessMessageService;
import cn._51even.wireless.utils.RadiusUtil;
import cn._51even.wireless.web.service.api.WirelessCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

@Service("wirelessCollectService")
public class WirelessCollectServiceImpl implements WirelessCollectService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WirelessMessageService wirelessMessageService;

}
