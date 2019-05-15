/**
 * Copyright 2018 MTC http://www.mtcsys.com/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.mtc.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-11-15
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 数据权限过滤
     */
    public static final String SQL_FILTER = "sql_filter";
    /**
     * Y
     */
    public static final String Yes = "Y";
    /**
     * N
     */
    public static final String No = "N";
    /**
     * 空
     */
    public static final String EMPTY = "";

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * PDF
     */
    public enum PDFType {
        无("", "0"),
        数量单价折扣("01.rpt", "1"),
        数量大字体("02.rpt", "2"),
        库房数量收款("03.rpt", "3"),
        数量收款("04.rpt", "4"),
        数量备注("05.rpt", "5"),
        数量("06.rpt", "6"),
        数量折扣金额("07.rpt", "7"),
        折扣兑现("08.rpt", "8"),
        退货_现金("81.rpt", "81"),
        库存转储("91.rpt", "91"),
        库存转储请求("92.rpt", "92");

        private String value;
        private String sapKey;

        PDFType(String value, String sapKey) {
            this.value = value;
            this.sapKey = sapKey;
        }

        public String getValue() {
            return value;
        }

        public String getSapKey() {
            return sapKey;
        }

        public static PDFType value(String code) {
            for (PDFType typeEnum : PDFType.values()) {
                if (typeEnum.sapKey.equals(code)) {
                    return typeEnum;
                }
            }
            return PDFType.无;
        }
    }

    /**
     * 组织架构类型
     */
    public enum OrganizationType {

        /**
         * 事业部
         */
        DIVISION("A"),

        /**
         * 注册法人
         */
        COMPANY("B"),
        /**
         * 部门
         */
        DEPARTMENT("C"),
        /**
         * 经营单元
         */
        SALEUNIT("D");
        private String type;

        OrganizationType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum DeptType {
        /**
         * 产品
         */
        PRODUCTION("A"),
        /**
         * 销售
         */
        SALES("B"),
        /**
         * 管理
         */
        MANAGE("C"),
        /**
         * 技术
         */
        TECH("D");
        private String type;

        DeptType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }

    /**
     * 预算组织架构类型
     */
    public enum BudgetOrgaType {
        /**
         * 注册法人
         */
        COMPANY("A"),
        /**
         * 经营单元
         */
        MANAGE("B"),
        /**
         * 销售单元
         */
        SALE("C");
        private String type;

        BudgetOrgaType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 预算类型
     */
    public enum BudgetType {
        /**
         * 岗位人员预算
         */
        userdept(""),
        /**
         * 不可控费用
         */
        uncont("NCTR"),
        /**
         * 可控固定费用
         */
        fixcont("CTRFIX"),
        /**
         * 可控变动费用
         */
        chancont("CTRVAR"),

        /**
         * 产品配销差-按公司
         */
        diffcomp(""),

        /**
         * 产品配销差-按销售单元
         */
        diffsales(""),

        /**
         * 产品销量预算
         */
        prodsale(""),

        /**
         * 销售单元预算审核
         */
        saleUnitApprove(""),

        /**
         * 经营单元预算审核
         */
        buzUnitApprove(""),

        /**
         * 产品线预算审核
         */
        proLineApprove(""),

        /**
         * 注册公司预算审核
         */
        companyApprove("");

        private String code;

        BudgetType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 草稿类型
     */
    public enum DraftType {

        /**
         * 订单
         */
        ORDER("17"),
        /**
         * 交货
         */
        DELIVERY("15"),
        /**
         * 退货
         */
        RETURN("16");

        DraftType(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
