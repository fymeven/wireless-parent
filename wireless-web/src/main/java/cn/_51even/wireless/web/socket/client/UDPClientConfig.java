package cn._51even.wireless.web.socket.client;

import cn._51even.wireless.web.socket.handler.UDPChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class UDPClientConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${udp.sendPort}")
    private int sendPort;

    @Resource
    private UDPChannelInitializer udpChannelInitializer;

    /**
     * 配置udp服务器
     * @return
     */
    @Bean("udpClient")
    public Channel udpServer(){
        EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
//                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(udpChannelInitializer)
            ;
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.bind(sendPort).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (channelFuture.isSuccess()){
            logger.debug("UDP client已启动,port:{}",sendPort);
        }
        Channel channel = channelFuture.channel();
        return channel;
    }

}
