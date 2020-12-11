package cn._51even.wireless.web.service.business;

import cn._51even.wireless.bean.entity.WirelessMessage;
import cn._51even.wireless.core.base.exception.BusinessException;
import cn._51even.wireless.core.util.UUIDUtil;
import cn._51even.wireless.service.api.WirelessMessageService;
import cn._51even.wireless.model.AttrTLV;
import cn._51even.wireless.utils.RadiusUtil;
import cn._51even.wireless.utils.WirelessDataHandler;
import cn._51even.wireless.web.service.api.WirelessCollectService;
import cn._51even.wireless.web.socket.NettySocketHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

@Service("wirelessCollectService")
public class WirelessCollectServiceImpl implements WirelessCollectService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WirelessMessageService wirelessMessageService;
    @Value("udp.Key")
    private String Key;

    @Transactional
    @Override
    public void handleReceiveWireless(ChannelHandlerContext ctx, DatagramPacket datagramPacket) {
        StringBuilder hexBuilder = new StringBuilder();
        InetSocketAddress sender = datagramPacket.sender();
        String remoteIp = sender.getAddress().getHostAddress();
        int remotePort = sender.getPort();
        ByteBuf buf = datagramPacket.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        //通过ByteBuf的readBytes方法将缓冲区中的字节数组复制到新建的byte数组中
        buf.readBytes(req);
        String hexString = RadiusUtil.getHexString(req);
        Integer lastOffset = WirelessDataHandler.MFLD_OFFSET_END_MAP.get(WirelessDataHandler.MFLD.AttrNum);
        if (StringUtils.isBlank(hexString) || hexString.length() < lastOffset){
            throw new BusinessException("数据报文不完整:"+hexString);
        }
        hexBuilder.append(hexString);
        String Attr = WirelessDataHandler.getMFLD(hexBuilder, WirelessDataHandler.MFLD.Attr);
        List<AttrTLV> attrTLVList = WirelessDataHandler.parseAttr(new StringBuilder(Attr), 0);
        if (attrTLVList == null){
            throw new BusinessException("解析Attr报文属性字段错误:"+Attr);
        }
        if (attrTLVList.isEmpty()){
            throw new BusinessException("Attr报文属性字段为空:"+Attr);
        }
        for (AttrTLV attrTLV : attrTLVList) {
            switch (attrTLV.getAttrType()){
                case SMS:
                    WirelessDataHandler.parseSmsValue(attrTLV.getAttrValue());
                    break;
            }
        }
        try {
            WirelessMessage wirelessMessage = new WirelessMessage();
            wirelessMessage.setId(UUIDUtil.getUUID());
            wirelessMessage.setReceiveData(hexString);
            wirelessMessage.setReceiveTime(new Date());
            wirelessMessage.setRemoteIp(remoteIp);
            wirelessMessage.setRemotePort(remotePort);
            boolean save = wirelessMessageService.save(wirelessMessage);
            if (save){
                String Type = WirelessDataHandler.getMFLD(hexBuilder, WirelessDataHandler.MFLD.Type);
                WirelessDataHandler.Type typeEnum = WirelessDataHandler.Type.getEnum(Type);
                switch (typeEnum){
                    case REQ_JOIN://连接请求
                        handleReqJoin(remoteIp,remotePort);
                        WirelessDataHandler.putMFLD(hexBuilder,WirelessDataHandler.MFLD.Type,WirelessDataHandler.Type.ACK_JOIN.getValue());
                        String KeyHex = Hex.encodeHexString(Key.getBytes());
                        WirelessDataHandler.putMFLD(hexBuilder,WirelessDataHandler.MFLD.Key,KeyHex);
                        break;
                    case REQ_TRANSFDATA: //报文请求

                        WirelessDataHandler.putMFLD(hexBuilder,WirelessDataHandler.MFLD.Type,WirelessDataHandler.Type.ACK_TRANSFDATA.getValue());
                        break;
                    case REQ_STATUS://心跳状态请求

                        WirelessDataHandler.putMFLD(hexBuilder,WirelessDataHandler.MFLD.Type,WirelessDataHandler.Type.ACK_STATUS.getValue());
                        break;
                }
            }else {
                WirelessDataHandler.putMFLD(hexBuilder, WirelessDataHandler.MFLD.ErrCode,WirelessDataHandler.ErrCode.RE_ERR.getValue());
            }
            String SerialNo = WirelessDataHandler.getMFLD(hexBuilder, WirelessDataHandler.MFLD.SerialNo);
            WirelessDataHandler.putMFLD(hexBuilder,WirelessDataHandler.MFLD.ReqID,SerialNo);
        }catch (Exception e){
            WirelessDataHandler.putMFLD(hexBuilder, WirelessDataHandler.MFLD.ErrCode,WirelessDataHandler.ErrCode.RE_ERR.getValue());
            logger.error("服务器接收数据异常，客户端需重新发送该条数据:{}",e.getMessage());
        }finally {
            try {
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(Hex.decodeHex(hexBuilder.toString())), sender));
            } catch (DecoderException e) {
                logger.error("响应udp客户端失败:{}",e.getMessage());
            }
        }
    }

    //加入连接
    private void handleReqJoin(String remoteIp, Integer remotePort){
        String socket = remoteIp+":"+remotePort;
        NettySocketHolder.addSocket(socket);
    }

    //报文数据处理
    private void handleReqTransfData(String hex){

    }

    //心跳处理
    private void handleReqStatus(String remoteIp, Integer remotePort){

    }

}
