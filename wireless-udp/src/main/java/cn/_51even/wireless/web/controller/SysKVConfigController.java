package cn._51even.wireless.web.controller;

import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.service.api.SysKVConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/sysKVConfig")
@Api(value = "sysKVConfig" ,tags = "系统KV配置")
@Validated
public class SysKVConfigController {

    @Resource
    private SysKVConfigService sysKVConfigService;

    @PostMapping("/setConfig")
    @ApiOperation(value = "setConfig",notes = "更改配置")
    public ResponseResult setConfig(@NotBlank(message = "key不能为空")
                                    @RequestParam(required = false)String key,
                                    @NotBlank(message = "value不能为空")
                                    @RequestParam(required = false)String value) {
        return sysKVConfigService.set(key,value);
    }

    @GetMapping("/configList")
    @ApiOperation(value = "configList",notes = "系统配置列表")
    public ResponseResult configList() {
        return sysKVConfigService.configList();
    }

}
