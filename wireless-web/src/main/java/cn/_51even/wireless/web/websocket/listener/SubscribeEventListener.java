package cn._51even.wireless.web.websocket.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.Map;


@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent>{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
		Principal principal = event.getUser();
		if (principal != null){
			String user = principal.getName();
//			JSONObject jsonObject = JSONObject.parseObject(user);
//			SysUser sysUser = JSONObject.toJavaObject(jsonObject, SysUser.class);
			logger.debug("---用户:{}订阅了[topic:{}]webSocket会话连接",user,((Map)event.getMessage().getHeaders().get("nativeHeaders")).get("destination"));
		}
	}

}
