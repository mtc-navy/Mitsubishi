package io.mtc.modules.sap.constant;

/**
 * 常量
 */
public class Constant {

    /**
     * Y/N枚举类
     */
    public enum YesOrNoEnum {

        No("0", "N"),
        Yes("1", "Y");

        private String value;
        private String name;

        YesOrNoEnum(String _value, String _name) {
            this.value = _value;
            this.name = _name;
        }

        public static YesOrNoEnum value(String _value) {
            for (YesOrNoEnum typeEnum : YesOrNoEnum.values()) {
                if (typeEnum.value.equals(_value)) {
                    return typeEnum;
                }
            }
            return YesOrNoEnum.No;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 计划单单据状态
     * 0-可修改，1-不可修改，2-已取消，3-已结算
     */
    public enum PlanDocStatus {

        Empty("", "未定义"),
        CanEdit("0", "可修改"),
        CannotEdit("1", "不可修改"),
        Canceled("2", "已取消"),
        Closed("3", "已结算");

        private String value;
        private String name;

        PlanDocStatus(String _value, String _name) {
            this.value = _value;
            this.name = _name;
        }

        public static PlanDocStatus value(String _value) {
            for (PlanDocStatus typeEnum : PlanDocStatus.values()) {
                if (typeEnum.value.equals(_value)) {
                    return typeEnum;
                }
            }
            return PlanDocStatus.Empty;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 计划单单据行状态
     * O-未清，C-已取消，L-已结算
     */
    public enum PlanLineDocStatus {

        Empty("", "未定义"),
        Open("O", "未清"),
        Canceled("C", "已取消"),
        Closed("L", "已结算");

        private String value;
        private String name;

        PlanLineDocStatus(String _value, String _name) {
            this.value = _value;
            this.name = _name;
        }

        public static PlanLineDocStatus value(String _value) {
            for (PlanLineDocStatus typeEnum : PlanLineDocStatus.values()) {
                if (typeEnum.value.equals(_value)) {
                    return typeEnum;
                }
            }
            return PlanLineDocStatus.Empty;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 计划单单据状态
     * 0-草稿，1-审批中，2-未批准，3-已生效，4-待过一磅，5-提货中，6-待过二磅，7-已出库，8-已作废
     */
    public enum WorkOrderDocStatus {

        Empty("", "未定义"),
        Draft("0", "草稿"),
        Passing("1", "已取消"),
        UnPass("2", "未批准"),
        Passed("3", "已生效"),
        APound("4", "待过一磅"),
        Taking("5", "提货中"),
        BPound("6", "待过二磅"),
        Outputed("7", "已出库"),
        Canceled("8", "已作废");

        private String value;
        private String name;

        WorkOrderDocStatus(String _value, String _name) {
            this.value = _value;
            this.name = _name;
        }

        public static WorkOrderDocStatus value(String _value) {
            for (WorkOrderDocStatus typeEnum : WorkOrderDocStatus.values()) {
                if (typeEnum.value.equals(_value)) {
                    return typeEnum;
                }
            }
            return WorkOrderDocStatus.Empty;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 计划单单据行状态
     * O-未清，C-已取消，L-已结算
     */
    public enum WorkOrderLineDocStatus {

        Empty("", "未定义"),
        Open("O", "未清"),
        Closed("L", "已结算");

        private String value;
        private String name;

        WorkOrderLineDocStatus(String _value, String _name) {
            this.value = _value;
            this.name = _name;
        }

        public static WorkOrderLineDocStatus value(String _value) {
            for (WorkOrderLineDocStatus typeEnum : WorkOrderLineDocStatus.values()) {
                if (typeEnum.value.equals(_value)) {
                    return typeEnum;
                }
            }
            return WorkOrderLineDocStatus.Empty;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
