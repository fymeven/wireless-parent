package cn._51even.wireless.web.socket.udp.config;

import cn._51even.wireless.web.socket.udp.handler.UDPChannelInitializer;
import cn._51even.wireless.web.socket.udp.handler.UDPServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class UDPServerConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${udp.listenPort}")
    private int listenPort;
    @Resource
    private UDPChannelInitializer udpChannelInitializer;

    /**
     * 配置udp服务器
     * @return
     */
    @Bean("udpServer")
    public EventLoopGroup udpServer(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(udpChannelInitializer)
            ;
            ChannelFuture channelFuture = bootstrap.bind(listenPort).sync();
            if (channelFuture.isSuccess()){
                logger.debug("UDP server已启动,port:{}",listenPort);
            }
            channelFuture.channel().closeFuture().await();
        }catch (Exception e){
            logger.error("启动 UDP server 异常:{}",e.getMessage());
        }
        finally {
            group.shutdownGracefully();
        }
        return group;
    }
}
