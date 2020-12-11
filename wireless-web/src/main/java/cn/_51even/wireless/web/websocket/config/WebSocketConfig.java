package cn._51even.wireless.web.websocket.config;

import cn._51even.wireless.web.websocket.handler.MyHandshakeHandler;
import cn._51even.wireless.web.websocket.interceptor.ClientInboundChannelInterceptor;
import cn._51even.wireless.web.websocket.interceptor.ClientOutboundChannelInterceptor;
import cn._51even.wireless.web.websocket.interceptor.WebSocketHandShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private MyHandshakeHandler myHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WebSocketConstont.WS_ENDPOINT)
                .setAllowedOrigins("*")
                ;
        registry.addEndpoint(WebSocketConstont.STOMP_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WebSocketConstont.BROKER_TOPIC,WebSocketConstont.BROKER_USER);
        registry.setApplicationDestinationPrefixes(WebSocketConstont.BROKER_APP);

//        registry.enableStompBrokerRelay(WebSocketConstont.BROKER_TOPIC,WebSocketConstont.BROKER_USER)
//                .setRelayHost(rabbitProperties.getHost())
//                .setRelayPort(rabbitProperties.getPort())
//                .setClientLogin(rabbitProperties.getUsername())
//                .setClientPasscode(rabbitProperties.getPassword())
//                .setSystemLogin(rabbitProperties.getUsername())
//                .setSystemPasscode(rabbitProperties.getPassword())
//                .setSystemHeartbeatSendInterval(5000)
//                .setSystemHeartbeatReceiveInterval(4000)
//                ;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ClientInboundChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ClientOutboundChannelInterceptor());
    }
}
