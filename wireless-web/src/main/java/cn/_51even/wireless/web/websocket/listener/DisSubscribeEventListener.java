package cn._51even.wireless.web.websocket.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.Map;


@Component
public class DisSubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent>{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		Principal principal = event.getUser();
		if (principal != null){
			String user = principal.getName();
//			JSONObject jsonObject = JSONObject.parseObject(user);
//			SysUser sysUser = JSONObject.toJavaObject(jsonObject, SysUser.class);
			logger.debug("---用户:{}取消订阅了[topic:{}]webSocket会话连接",user,((Map)event.getMessage().getHeaders().get("nativeHeaders")).get("destination"));
		}
	}
}
