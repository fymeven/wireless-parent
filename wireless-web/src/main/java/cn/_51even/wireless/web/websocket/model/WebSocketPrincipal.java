package cn._51even.wireless.web.websocket.model;

import java.security.Principal;

public class WebSocketPrincipal implements Principal {

    private String user;

    public WebSocketPrincipal(String user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user;
    }
}
