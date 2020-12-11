package cn._51even.wireless.web.socket.handler;

import cn._51even.wireless.web.service.api.WirelessCollectService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WirelessCollectService wirelessCollectService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) {
        logger.debug("接收到客户端socket信息..");
        wirelessCollectService.handleReceiveWireless(ctx,datagramPacket);
    }

}
