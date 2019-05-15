package io.mtc.modules.sap.entity;

import java.io.Serializable;

/**
 * 仓库
 */
public class WarehouseEntity implements Serializable {

    /**
     * 仓库代码
     */
    private String WhsCode;

    /**
     * 仓库名称
     */
    private String WhsName;

    /**
     * 分支ID
     */
    private Integer BPLid;

    /**
     * 仓库分类
     */
    private String U_U_CIOTY1;

    /**
     * 仓库性质
     */
    private String U_U_CIOTY2;
    /**
     * 简称
     */
    private String ShortName;

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getWhsName() {
        return WhsName;
    }

    public void setWhsName(String whsName) {
        WhsName = whsName;
    }

    public Integer getBPLid() {
        return BPLid;
    }

    public void setBPLid(Integer BPLid) {
        this.BPLid = BPLid;
    }

    public String getU_U_CIOTY1() {
        return U_U_CIOTY1;
    }

    public void setU_U_CIOTY1(String u_U_CIOTY1) {
        U_U_CIOTY1 = u_U_CIOTY1;
    }

    public String getU_U_CIOTY2() {
        return U_U_CIOTY2;
    }

    public void setU_U_CIOTY2(String u_U_CIOTY2) {
        U_U_CIOTY2 = u_U_CIOTY2;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }
}
