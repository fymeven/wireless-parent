package cn._51even.wireless.service.business;

import cn._51even.wireless.core.base.bean.response.ResponseResult;
import cn._51even.wireless.core.base.exception.BusinessException;
import cn._51even.wireless.core.base.service.BaseService;
import cn._51even.wireless.bean.entity.SysKvConfig;
import cn._51even.wireless.bean.enums.SysKvConfigEnum;
import cn._51even.wireless.core.redis.RedisStringUtil;
import cn._51even.wireless.service.api.SysKVConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysKVConfigServiceImpl extends BaseService<SysKvConfig,String> implements SysKVConfigService {

    @Override
    public String get(String key) {
        String value = null;
        if (RedisStringUtil.exists(key)){
            value = (String) RedisStringUtil.get(key);
            return value;
        }
        Example example = new Example(SysKvConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey",key);
        SysKvConfig sysKvConfig = selectOne(example);
        if (sysKvConfig !=null){
            value = sysKvConfig.getConfigValue();
        }else {
            SysKvConfigEnum.param param = SysKvConfigEnum.param.getEnumByKey(key);
            value = param.getValue();
            SysKvConfig insertEntity = new SysKvConfig();
            insertEntity.setConfigKey(key);
            insertEntity.setConfigValue(value);
            insertEntity.setConfigMark(param.getMark());
            save(insertEntity);
        }
        String expireTime = null;
        if (Objects.equals(SysKvConfigEnum.param.CACHE_EXPIRE_TIME.getKey(),key)){
            expireTime = value;
        }else{
            expireTime = get(SysKvConfigEnum.param.CACHE_EXPIRE_TIME.getKey());
        }
        RedisStringUtil.set(key,value,Long.valueOf(expireTime));
        return value;
    }

    @Transactional
    @Override
    public ResponseResult set(String key, String value) {
        boolean check = SysKvConfigEnum.param.check(key, value);
        if (!check){
            throw new BusinessException("参数验证不通过!");
        }
        boolean result = false;
        SysKvConfig configResult = selectById(key);
        if (configResult != null){
            SysKvConfig updateConfig = new SysKvConfig();
            updateConfig.setConfigKey(key);
            updateConfig.setConfigValue(value);
            result = updateNotNull(updateConfig);
        }else {
            SysKvConfig insertConfig = new SysKvConfig();
            insertConfig.setConfigKey(key);
            insertConfig.setConfigValue(value);
            SysKvConfigEnum.param param = SysKvConfigEnum.param.getEnumByKey(key);
            insertConfig.setConfigMark(param.getMark());
            result = save(insertConfig);
        }
        if (result){
            String expireTime = null;
            if (Objects.equals(SysKvConfigEnum.param.CACHE_EXPIRE_TIME.getKey(),key)){
                expireTime = value;
            }else{
                expireTime = get(SysKvConfigEnum.param.CACHE_EXPIRE_TIME.getKey());
            }
            RedisStringUtil.set(key,value,Long.valueOf(expireTime));
            return ResponseResult.SUCCESS;
        }
        return ResponseResult.errorMsg("操作失败!");
    }

    @Override
    public ResponseResult configList() {
        List<SysKvConfig> list = new ArrayList<>();
        SysKvConfigEnum.param[] values = SysKvConfigEnum.param.values();
        for (SysKvConfigEnum.param value : values) {
            SysKvConfig sysKvConfig = new SysKvConfig();
            String key = value.getKey();
            sysKvConfig.setConfigKey(key);
            sysKvConfig.setConfigValue(get(key));
            sysKvConfig.setConfigMark(value.getMark());
            list.add(sysKvConfig);
        }
        return ResponseResult.successData(list);
    }
}
