package cn._51even.wireless.core.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnExpression("'${plugin.uploadFile}'.equals('oss')")
@ConditionalOnClass(name = "com.aliyun.oss.OSS")
@Configuration
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {

    private String endpoint;

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Bean(name = "ossClient")
    public OSS ossClient(){
        OSS ossClient = new OSSClientBuilder().build(this.getEndpoint(), this.getAccessKeyId(), this.getAccessKeySecret());
        return ossClient;
    }
}
