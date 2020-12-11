package cn._51even.wireless.service.api;

import cn._51even.wireless.core.base.bean.response.ResponseResult;

public interface SysKVConfigService {

    String get(String key);

    ResponseResult set(String key, String value);

    ResponseResult configList();
}
