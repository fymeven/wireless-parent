package cn._51even.wireless.web.controller;

import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.service.api.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(value = "file" ,tags = "文件")
@RestController
@RequestMapping("/file")
public class SysFileController {

    @Resource
    private SysFileService sysFileService;

    @PostMapping(value = "/uploadFile",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "uploadFile",notes = "文件上传")
    public ResponseResult uploadFile(@RequestParam(required = false,name = "file") MultipartFile file){
        String url = sysFileService.uploadFile(file);
        return ResponseResult.successData(url);
    }

}
