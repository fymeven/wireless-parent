package cn._51even.wireless.web.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import cn._51even.wireless.bean.model.cas.SysUser;
import cn._51even.wireless.web.websocket.model.WebSocketModel;
import io.swagger.annotations.Api;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@Api(value = "webSocket" ,tags = "webSocket")
@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/news")
    public void news(){
        WebSocketModel webSocketModel = new WebSocketModel();
        webSocketModel.setSendUser("system");
        webSocketModel.setMessageContent("订阅成功!");
        webSocketModel.setMessageType(1);
        webSocketModel.setReceiveUser("all");
        simpMessagingTemplate.convertAndSend("/topic/news",webSocketModel);
    }

    @MessageMapping("/msg")
    public void p2p(WebSocketModel webSocketModel, Principal principal){
        String name = principal.getName();
        webSocketModel.setSendUser(name);
        webSocketModel.setReceiveUser(name);
        simpMessagingTemplate.convertAndSend("/topic/msg/"+name,webSocketModel);
    }

    @MessageMapping("/investigation")
    public void investigation(WebSocketModel webSocketModel, Principal principal){
        String name = principal.getName();
        SysUser sysUser = JSONObject.toJavaObject(JSONObject.parseObject(name), SysUser.class);
        webSocketModel.setSendUser(sysUser.getUserName());
        simpMessagingTemplate.convertAndSendToUser(webSocketModel.getSendUser(),"/investigation",webSocketModel);
    }
}
