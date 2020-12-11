package cn._51even.wireless.core.oss;

import cn._51even.wireless.core.util.UUIDUtil;
import com.aliyun.oss.OSS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@ConditionalOnExpression("'${plugin.uploadFile}'.equals('oss')")
@ConditionalOnClass(name = "com.aliyun.oss.OSS")
@Configuration
public class OSSUtil {

    private final static Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private static OSSProperties ossProperties;

    private static OSS ossClient;

    @Autowired(required = false)
    public void setOssProperties(OSSProperties ossProperties) {
        OSSUtil.ossProperties = ossProperties;
    }

    @Autowired(required = false)
    public void setOssClient(OSS ossClient) {
        OSSUtil.ossClient = ossClient;
    }

    public static String upload(InputStream inputStream, String fileName){
        String prefix = fileName.substring(0,fileName.indexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String objectName = new StringBuilder(prefix).append("_").append(UUIDUtil.getUUID()).append(suffix).toString();
        ossClient.putObject(ossProperties.getBucketName(), objectName, inputStream);
        return new StringBuilder().toString();
    }

    public static String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String prefix = fileName.substring(0,fileName.indexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String objectName = new StringBuilder(prefix).append("_").append(UUIDUtil.getUUID()).append(suffix).toString();
            ossClient.putObject(ossProperties.getBucketName(), objectName, inputStream);
            return new StringBuilder().toString();
        } catch (IOException e) {
            logger.error("文件上传失败,cause:{}",e);
            return null;
        }
    }
}
