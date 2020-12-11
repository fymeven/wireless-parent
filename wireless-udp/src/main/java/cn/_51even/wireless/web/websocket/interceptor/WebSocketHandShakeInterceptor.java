package cn._51even.wireless.web.websocket.interceptor;

import java.util.Enumeration;
import java.util.Map;


import cn._51even.wireless.bean.model.cas.SysUser;
import cn._51even.wireless.web.websocket.config.WebSocketConstont;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
		try {
			if (serverHttpRequest instanceof ServletServerHttpRequest){
				ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
				HttpServletRequest httpServletRequest = request.getServletRequest();
				Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
				for (Map.Entry<String, Object> header : attributes.entrySet()) {
					logger.debug("header:{}={}",header.getKey(),header.getValue());
				}
				String token = httpServletRequest.getParameter("token");
				if (StringUtils.isNotBlank(token)){
//					attributes.put(WebSocketConstont.WEB_SOCKET_USER_KEY,sysUser.getId());
					return true;
				}
			}
		}catch (Exception e){
			logger.error("webSocket连接失败!cause:{}",e);
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

	}
}
