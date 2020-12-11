package cn._51even.wireless.service.business;

import cn._51even.wireless.bean.entity.WirelessMessage;
import cn._51even.wireless.bean.request.wirelessMessage.PageWirelessMessageReq;
import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.core.base.service.BaseService;
import cn._51even.wireless.service.api.WirelessMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class WirelessMessageServiceImpl extends BaseService<WirelessMessage,String> implements WirelessMessageService {

    @Override
    public ResponseResult pageList(PageWirelessMessageReq pageWirelessMessageReq) {
        Example example = new Example(WirelessMessage.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(pageWirelessMessageReq.getRemoteIp())){
            criteria.andEqualTo("remoteIp",pageWirelessMessageReq.getRemoteIp());
        }
        if (StringUtils.isNotBlank(pageWirelessMessageReq.getRemotePort())){
            criteria.andEqualTo("remotePort",pageWirelessMessageReq.getRemotePort());
        }
        if (StringUtils.isNotBlank(pageWirelessMessageReq.getReceiveData())){
            criteria.andLike("receiveData","%"+pageWirelessMessageReq.getReceiveData()+"%");
        }
        if (pageWirelessMessageReq.getReceiveTimeStart() != null){
            criteria.andGreaterThanOrEqualTo("receiveTime",pageWirelessMessageReq.getReceiveTimeStart());
        }
        if (pageWirelessMessageReq.getReceiveTimeEnd() != null){
            criteria.andLessThanOrEqualTo("receiveTime",pageWirelessMessageReq.getReceiveTimeEnd());
        }
        if (pageWirelessMessageReq.getDoSort().booleanValue()) {
            if (StringUtils.isBlank(pageWirelessMessageReq.getSortColumn())) {
                pageWirelessMessageReq.setSortColumn("receive_time DESC,id DESC");
            }
            PageHelper.orderBy(pageWirelessMessageReq.getSortColumn());
        }
        if (pageWirelessMessageReq.getDoPage().booleanValue()) {
            PageHelper.startPage(pageWirelessMessageReq.getPageNum().intValue(), pageWirelessMessageReq.getPageSize().intValue());
        }
        List<WirelessMessage> list = select(example);
        if (pageWirelessMessageReq.getDoPage().booleanValue()) {
            return ResponseResult.successData(new PageInfo(list));
        }
        return ResponseResult.successData(list);
    }
}
