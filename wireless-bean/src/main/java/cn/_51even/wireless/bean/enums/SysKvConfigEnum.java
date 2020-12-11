package cn._51even.wireless.bean.enums;

import cn._51even.wireless.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class SysKvConfigEnum {

    public enum param {
        APP_EN("app_en","wireless_collection_system","应用名称(英文)"),
        APP_CN("app_cn","无线采集系统","应用名称(中文)"),
        CACHE_EXPIRE_TIME("cache_expire_time","86400","系统缓存时间(单位:秒)"),
        ;

        private String key;

        private String value;

        private String mark;

        param(String key, String value, String mark) {
            this.key = key;
            this.value = value;
            this.mark = mark;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getMark() {
            return mark;
        }

        public static param getEnumByKey(String key){
            param[] values = param.values();
            for (param value : values) {
                if (Objects.equals(value.getKey(),key)){
                    return value;
                }
            }
            return null;
        }

        public static boolean check(String key,String value){
            if (StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                return false;
            }
            param p = getEnumByKey(key);
            if (p == null){
                throw new BusinessException("系统不支持此配置!");
            }
            String regex = null;
            switch (p){
                case APP_EN:
                    regex = "[a-zA-Z]+";//判断是否全英文
                    boolean enMatch = value.matches(regex);
                    if (!enMatch){
                        throw new BusinessException(p.getMark()+"只支持输入英文!");
                    }
                    break;
                case CACHE_EXPIRE_TIME:
                    regex = "^[-\\+]?[\\d]*$";//判断是否全数字
                    boolean numberMatch = value.matches(regex);
                    if (!numberMatch){
                        throw new BusinessException(p.getMark()+"只支持输入数字!");
                    }
                    break;
            }
            return true;
        }
    }

}
