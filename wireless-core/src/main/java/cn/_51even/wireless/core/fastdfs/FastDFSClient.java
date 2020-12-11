package cn._51even.wireless.core.fastdfs;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


@ConditionalOnExpression("'${plugin.uploadFile}'.equals('fastdfs')")
@ConditionalOnClass(name = "com.github.tobato.fastdfs.domain.conn.FdfsWebServer")
@Configuration
public class FastDFSClient {

    private final static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    private static FastFileStorageClient storageClient;

    private static FdfsWebServer fdfsWebServer;

    @Autowired(required = false)
    public void setStorageClient(FastFileStorageClient storageClient) {
        FastDFSClient.storageClient = storageClient;
    }

    @Autowired(required = false)
    public void setFdfsWebServer(FdfsWebServer fdfsWebServer) {
        FastDFSClient.fdfsWebServer = fdfsWebServer;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file) {
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
            return getResAccessUrl(storePath);
        } catch (IOException e) {
            logger.error("文件上传失败,cause:{}",e);
            return null;
        }
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public static String uploadFile(File file){
        try {
            FileInputStream inputStream = new FileInputStream (file);
            StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()),null);
            return getResAccessUrl(storePath);
        } catch (IOException e) {
            logger.error("文件上传失败,cause:{}",e);
            return null;
        }
    }

    public static String uploadFile(InputStream is, long size, String fileName) {
        StorePath path = storageClient.uploadFile(is, size, fileName, null);
        return getResAccessUrl(path);
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public static String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }

    // 封装图片完整URL地址
    private static String getResAccessUrl(StorePath storePath) {
        String fileUrl = fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @return
     */
    public static void download(String fileUrl, HttpServletResponse response) {
        try {
            int pos = fileUrl.indexOf("group");
            String groupAndPath = fileUrl.substring(pos);
            pos = groupAndPath.indexOf("/");
            String group = groupAndPath.substring(0, pos);
            String path = groupAndPath.substring(pos + 1);
            byte[] data = storageClient.downloadFile(group, path, new DownloadByteArray());
            response.setCharacterEncoding("UTF-8");
            int suffixIndex = fileUrl.lastIndexOf(".");
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,suffixIndex);
            String suffix = fileUrl.substring(suffixIndex);
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName+suffix, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        }catch (Exception e){
            logger.error("下载文件失败,cause:{}",e);
        }
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public static void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            int pos = fileUrl.indexOf("group");
            String groupAndPath = fileUrl.substring(pos);
            pos = groupAndPath.indexOf("/");
            String group = groupAndPath.substring(0, pos);
            String path = groupAndPath.substring(pos + 1);
            storageClient.deleteFile(group, path);
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
}