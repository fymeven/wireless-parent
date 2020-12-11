package cn._51even.wireless.bean.enums;

public class SysDictTypeEnum {

    public enum dictStatus {
        ENABLE("1","启用"),
        DISABLE("2","禁用")
        ;

        private String code;

        private String desc;

        dictStatus(String code, String desc) {
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

    public enum deleteFlag {
        YES("1","已删除"),
        NO("0","使用中")
        ;

        private String code;

        private String desc;

        deleteFlag(String code, String desc) {
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
}
