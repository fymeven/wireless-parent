package cn._51even.wireless.web.websocket.handler;

import cn._51even.wireless.web.websocket.model.WebSocketPrincipal;
import cn._51even.wireless.web.websocket.config.WebSocketConstont;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String user = (String) attributes.get(WebSocketConstont.WEB_SOCKET_USER_KEY);
        return new WebSocketPrincipal(user);
    }
}
