package cn._51even.wireless.web.websocket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
public class ClientOutboundChannelInterceptor implements ChannelInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		logger.debug("往客户端发送消息:{}",message);
		return message;
	}

}
