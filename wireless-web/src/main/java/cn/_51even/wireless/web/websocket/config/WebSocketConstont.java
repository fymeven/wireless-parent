package cn._51even.wireless.web.websocket.config;

public class WebSocketConstont {

    //webSocket端点
    public static final String WS_ENDPOINT = "/ws";
    //stomp端点
    public static final String STOMP_ENDPOINT = "/stomp";
    //topic广播地址
    public static final String BROKER_TOPIC = "/topic";
    //客户端发送地址
    public static final String BROKER_APP = "/app";
    //p2p接收点至
    public static final String BROKER_USER = "/user";
    //当前用户key
    public static final String WEB_SOCKET_USER_KEY = "webSocketUserKey";
    //预警结果目的地
    public static final String DEST_RECORD_RESULT = "/dest_record_result";
    //webSocket在线用户
    public static final String WEB_SOCKET_ONLINE_USER = ":web_socket:online_user";
    //实时预警
    public static final String WEB_SOCKET_ACTUAL_ALARM = "/actualAlarm";
}
