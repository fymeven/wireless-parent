package cn._51even.wireless.core.base.exception;


import cn._51even.wireless.core.base.bean.enums.SystemEnum;

//自定义异常处理类
public class BusinessException extends RuntimeException {
    /**
     * 错误编码
     */
    private String code;
    /**
     * 错误信息说明
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public BusinessException(String message) {
        this.message=message;
    }

    public BusinessException(String code, String message){
        this.code=code;
        this.message=message;
    }

    public BusinessException(String code, String message,Object data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public BusinessException(SystemEnum.ErrorCode ErrorCode){
        this.code= ErrorCode.getCode();
        this.message= ErrorCode.getDesc();
    }

    public BusinessException(SystemEnum.ErrorCode ErrorCode, Object data){
        this.code= ErrorCode.getCode();
        this.message= ErrorCode.getDesc();
        this.data=data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
