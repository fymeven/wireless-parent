package cn._51even.wireless.web.socket.udp;

import io.netty.channel.socket.nio.NioDatagramChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class NettySocketHolder {

    //socket在线连接管理容器
    private static final Map<String, NioDatagramChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static Map<String, NioDatagramChannel> getChannelMap() {
        return CHANNEL_MAP;
    }

    public static NioDatagramChannel get(String key) {
        return CHANNEL_MAP.get(key);
    }

    public static void put(String key, NioDatagramChannel socketChannel) {
        CHANNEL_MAP.put(key, socketChannel);
    }

    public static void remove(NioDatagramChannel nioDatagramChannel) {
        CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioDatagramChannel).forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

//    public static String getKey(NioSocketChannel nioSocketChannel){
//        return CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).collect(Collectors.);
//    }

}
