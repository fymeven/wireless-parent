package cn._51even.wireless.web.controller;

import cn._51even.wireless.bean.request.wirelessMessage.PageWirelessMessageReq;
import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.service.api.WirelessMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/wirelessMessage")
@Api(value = "wirelessMessage" ,tags = "无线数据")
@Validated
public class WirelessMessageController {

    @Resource
    private WirelessMessageService wirelessMessageService;

    @GetMapping("/pageList")
    @ApiOperation(value = "pageList",notes = "查询字典列表")
    public ResponseResult pageList(@Valid PageWirelessMessageReq pageWirelessMessageReq) {
        return wirelessMessageService.pageList(pageWirelessMessageReq);
    }
}
