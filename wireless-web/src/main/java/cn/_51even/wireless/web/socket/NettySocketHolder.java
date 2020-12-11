package cn._51even.wireless.web.socket;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class NettySocketHolder {

    //socket在线连接管理容器
    private static final Set<String> ONLINE_SOCKET = new CopyOnWriteArraySet<>();

    public static void addSocket(String socket){
        ONLINE_SOCKET.add(socket);
    }

    public static void remove(String socket) {
        ONLINE_SOCKET.remove(socket);
    }

    public static int getOnlineCount() {
        return ONLINE_SOCKET.size();
    }

    public static Set<String> getOnlineSocket() {
        return ONLINE_SOCKET;
    }
}
