package cn._51even.wireless.web.socket.udp.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UDPChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${udp.heartBeat}")
    private int heartBeat;

    @Resource
    private UDPServerHandler udpServerHandler;

    @Override
    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
        ChannelPipeline pipeline = nioDatagramChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(heartBeat, 0, 0));
        pipeline.addLast("serverHandler", udpServerHandler);//消息处理器
    }

}
