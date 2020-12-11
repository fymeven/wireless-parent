package cn._51even.wireless.core.base.bean.enums;

public class SystemEnum {

    public enum ErrorCode {
        SERVICE_ERROR("S10001","系统错误，请联系管理员"),
        REQUEST_PARAM_ERROR("R30002","请求参数错误"),
        LOGIN_EXPIRE("L10001","未登录或登录已过期"),
        LOGIN_PKI_ERROR("L10002","PKI认证失败"),
        LOGIN_SSO_ERROR("L10003","SSO认证失败"),
        MISS_USER_INFO("M10001","未获取到用户信息")
        ;
        private String code;
        private String desc;

        ErrorCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    public enum UPLOAD_PLUGIN{
        FASTDFS,
        OSS
        ;
    }

    public enum MQ_PLUGIN{
        KAFKA,
        ROCKET_MQ
        ;
    }

}
