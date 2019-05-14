package io.mtc.modules.sap.constant;

/**
 * 单据状态
 */
public enum DocStatusEnum {

    草稿("D"),
    提交审核("S"),
    审核通过("P"),
    拒绝("R"),
    关闭("C");

    private String code;

    DocStatusEnum(String code) {
        this.code = code;
    }

    public static DocStatusEnum value(String code) {
        for (DocStatusEnum typeEnum : DocStatusEnum.values()) {
            if (typeEnum.code.equals(code)) {
                return typeEnum;
            }
        }
        return DocStatusEnum.草稿;
    }

    public String getCode() {
        return code;
    }
}
