package cn._51even.wireless.web.service.api;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public interface WirelessCollectService{

    void handleReceiveWireless(ChannelHandlerContext ctx, DatagramPacket datagramPacket);
}
