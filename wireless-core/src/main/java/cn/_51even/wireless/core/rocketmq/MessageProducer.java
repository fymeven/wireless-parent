package cn._51even.wireless.core.rocketmq;

import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.UUID;

@ConditionalOnExpression("'${plugin.messageMq}'.equals('rocketmq')")
@ConditionalOnClass(name = "com.aliyun.openservices.ons.api.Producer")
@Configuration
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private static final String TIME_OUT = "3000";

    private Producer producer = null;

    @Resource
    private RocketMqProperties rocketMqProperties;


    private Producer createProducer(){
        Properties properties = rocketMqProperties.getProperties();
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis,TIME_OUT);
        producer = ONSFactory.createProducer(properties);
        return producer;
    }

    public SendResult send(String topic,String tag,String message){
        if (producer == null){
            createProducer();
        }
        if (!producer.isStarted()){
            producer.start();
        }
        Message onsMsg = new Message(topic,tag,message.getBytes());
        onsMsg.setKey(UUID.randomUUID().toString());
        try {
            SendResult sendResult = producer.send(onsMsg);
            logger.info("消息发送成功:{}",sendResult);
            return sendResult;
        }catch (Exception e){
            logger.error("消息发送失败:{}",e);
            return null;
        }
    }

}
