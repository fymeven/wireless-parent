package cn._51even.wireless.service.api;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface SysFileService {

    void downloadFile(HttpServletResponse response,String filePath,String fileName);

    String uploadFile(MultipartFile file);

}
