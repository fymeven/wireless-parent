package cn._51even.wireless.web.socket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class UDPChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UDPServerHandler udpServerHandler;

    @Override
    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
        ChannelPipeline pipeline = nioDatagramChannel.pipeline();
        pipeline.addLast("serverHandler", udpServerHandler);//消息处理器
    }

}
