package cn._51even.wireless.service.business;

import cn._51even.wireless.core.base.bean.enums.SystemEnum;
import cn._51even.wireless.core.base.exception.BusinessException;
import cn._51even.wireless.core.oss.OSSUtil;
import cn._51even.wireless.core.fastdfs.FastDFSClient;
import cn._51even.wireless.service.api.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;


@Service
public class SysFileServiceImpl implements SysFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${plugin.uploadFile:fastdfs}")
    private String pluginName;

    @Override
    public void downloadFile(HttpServletResponse response, String filePath, String fileName) {
        BufferedInputStream bis = null;
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            response.setHeader("contentType","application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            byte[] buff = new byte[1024];
            bis = new BufferedInputStream(resourceAsStream);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buff);
            while ( i != -1){
                os.write(buff,0,buff.length);
                os.flush();
                i = bis.read(buff);
            }
        }catch (Exception e){
            throw new BusinessException("下载文件不存在");
        }finally {
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("关闭资源文件失败!cause:{}",e);
                }
            }
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String url = null;
        try {
            if (Objects.equals(SystemEnum.UPLOAD_PLUGIN.FASTDFS.name(),pluginName.toUpperCase())){
                url = FastDFSClient.uploadFile(file);
            }
            if (Objects.equals(SystemEnum.UPLOAD_PLUGIN.OSS.name(),pluginName.toUpperCase())){
                url = OSSUtil.upload(file);
            }
        }catch (Exception e){
            logger.error("获取文件失败:{}",e);
        }
        if (StringUtils.isNotBlank(url)){
            logger.debug("upload url:{}",url);
            return url;
        }else{
            throw new BusinessException("上传失败!");
        }
    }

}
