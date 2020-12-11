package cn._51even.wireless.core.rocketmq;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@ConditionalOnExpression("'${plugin.messageMq}'.equals('rocketmq')")
@ConditionalOnClass(name = "com.aliyun.openservices.ons.api.Producer")
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {

    private String onsAddr;

    private String groupId;

    private String accessKey;

    private String secretKey;

    private String threadNum;

    public Properties getProperties(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, this.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, this.getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr,this.getOnsAddr());
        properties.put(PropertyKeyConst.GROUP_ID, this.getGroupId());
        properties.put(PropertyKeyConst.ConsumeThreadNums, this.getThreadNum());
        return properties;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsAddr() {
        return onsAddr;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }

    public String getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(String threadNum) {
        this.threadNum = threadNum;
    }

}
