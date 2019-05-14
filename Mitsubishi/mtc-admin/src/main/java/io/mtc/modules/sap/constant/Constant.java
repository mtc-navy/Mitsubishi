package io.mtc.modules.sap.constant;

public class Constant {

    /**
     * 分支期间状态：已解锁
     */
    public static final String PeriodStatusUnLock = "N";

    /**
     * 订料单单据状态
     */
    public enum DesOrderDocStatusEnum {

        未清("O"),
        取消("C"),
        已结算("D");

        private String code;

        DesOrderDocStatusEnum(String code) {
            this.code = code;
        }

        public static DesOrderDocStatusEnum value(String code) {
            for (DesOrderDocStatusEnum typeEnum : DesOrderDocStatusEnum.values()) {
                if (typeEnum.code.equals(code)) {
                    return typeEnum;
                }
            }
            return DesOrderDocStatusEnum.未清;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 订料单单据状态
     */
    public enum PriceOrderDocStatusEnum {

        未清("O"),
        取消("C"),
        已结算("D");

        private String code;

        PriceOrderDocStatusEnum(String code) {
            this.code = code;
        }

        public static PriceOrderDocStatusEnum value(String code) {
            for (PriceOrderDocStatusEnum typeEnum : PriceOrderDocStatusEnum.values()) {
                if (typeEnum.code.equals(code)) {
                    return typeEnum;
                }
            }
            return PriceOrderDocStatusEnum.未清;
        }

        public String getCode() {
            return code;
        }
    }
}
