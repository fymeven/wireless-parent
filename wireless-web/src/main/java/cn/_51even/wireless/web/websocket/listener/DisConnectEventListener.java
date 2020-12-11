package cn._51even.wireless.web.websocket.listener;

import cn._51even.wireless.core.redis.RedisSetUtil;
import cn._51even.wireless.web.websocket.config.WebSocketConstont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class DisConnectEventListener implements ApplicationListener<SessionDisconnectEvent>{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		Principal principal = event.getUser();
		if (principal != null){
			String user = principal.getName();
//			JSONObject jsonObject = JSONObject.parseObject(user);
//			SysUser sysUser = JSONObject.toJavaObject(jsonObject, SysUser.class);
			RedisSetUtil.remove(WebSocketConstont.WEB_SOCKET_ONLINE_USER,user);
			logger.debug("---用户:{}退出webSocket会话连接",user);
			int size = RedisSetUtil.size(WebSocketConstont.WEB_SOCKET_ONLINE_USER);
			logger.debug("---当前在线人数:{}---",size);
		}
	}
}
