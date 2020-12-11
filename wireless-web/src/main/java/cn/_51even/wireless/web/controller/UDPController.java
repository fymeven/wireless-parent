package cn._51even.wireless.web.controller;

import cn._51even.wireless.bean.entity.WirelessMessage;
import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.utils.RadiusUtil;
import cn._51even.wireless.web.socket.NettySocketHolder;
import cn._51even.wireless.web.socket.UDPConstant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Set;

@Api(value = "udp" ,tags = "udp")
@RestController
@RequestMapping("/udp")
public class UDPController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "udpClient")
    private Channel udpClient;

    @PostMapping("/getOnlineSocket")
    @ApiOperation(value = "getOnlineSocket",notes = "获取在线设备列表")
    public ResponseResult getOnlineSocket(){
        Set<String> onlineSocketSet = NettySocketHolder.getOnlineSocket();
        return ResponseResult.successData(onlineSocketSet);
    }

    @PostMapping("/sendMsg")
    @ApiOperation(value = "sendMsg",notes = "发送消息到客户端")
    public ResponseResult sendMsg(@NotBlank(message = "请输入发送的消息")
                                  @RequestParam(required = false,name = "message") String message,
                                  @NotBlank(message = "请输入发送的目标ip")
                                  @RequestParam(required = false,name = "remoteAddress") String remoteAddress,
                                  @NotBlank(message = "请输入发送的目标端口")
                                  @RequestParam(required = false,name = "remotePort") Integer remotePort){
        try {
            String hexString = Hex.encodeHexString(message.getBytes());
            if (StringUtils.isNotBlank(remoteAddress) && remotePort != null){
                InetSocketAddress receiver = new InetSocketAddress(remoteAddress, remotePort);
                udpClient.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(Hex.decodeHex(hexString)), receiver));
            }else {
                Set<String> onlineSocket = NettySocketHolder.getOnlineSocket();
                for (String socket : onlineSocket) {
                    String[] split = socket.split(":");
                    InetSocketAddress receiver = new InetSocketAddress(split[0], Integer.valueOf(split[1]));
                    udpClient.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(Hex.decodeHex(hexString)), receiver));
                }
            }
            return ResponseResult.SUCCESS;
        }catch (Exception e){
            logger.error("udp消息发送失败!");
        }
        return ResponseResult.ERROR;
    }

    public static void main(String[] args) {
        try {
            ByteBuf byteBuf = Unpooled.copiedBuffer(Hex.decodeHex("002d"));
            System.out.println(byteBuf.toString());
        }catch (Exception e){

        }
    }


}
