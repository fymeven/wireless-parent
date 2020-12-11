package cn._51even.wireless.web.websocket.model;

public class WebSocketEnum {

    public enum messageType{
        PTP(1,"点对点"),
        PUBLISH(2,"广播")
        ;

        private int code;
        private String desc;

        messageType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
