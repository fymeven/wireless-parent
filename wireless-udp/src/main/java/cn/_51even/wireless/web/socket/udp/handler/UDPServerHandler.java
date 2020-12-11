package cn._51even.wireless.web.socket.udp.handler;

import cn._51even.wireless.web.socket.udp.NettySocketHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;

@Component
public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) {
        String message = datagramPacket.content().toString(CharsetUtil.UTF_8);
        logger.debug("接收到服务端消息:{}",message);
        String returnMsg = "收到消息了"+message;
        String key = datagramPacket.sender().getAddress() + ":" + datagramPacket.sender().getPort();
        NettySocketHolder.put(key,(NioDatagramChannel) ctx.channel());
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(returnMsg, CharsetUtil.UTF_8), datagramPacket.sender()));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                SocketAddress socketAddress = ctx.channel().remoteAddress();
                if (socketAddress != null){
                    NettySocketHolder.remove((NioDatagramChannel) ctx.channel());
                    logger.warn("断开连接:{}",socketAddress);
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}
