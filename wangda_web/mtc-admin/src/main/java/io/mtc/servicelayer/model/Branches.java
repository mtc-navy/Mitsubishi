package io.mtc.servicelayer.model;

import java.io.Serializable;

/**
 * 分支
 * Created by navy.jiang on 2018/9/3.
 */
public class Branches implements Serializable {

    private static final long serialVersionUID = -2319785858920760510L;

    /**
     * 分支编号
     */
    private Integer OBPLId;

    /**
     * 分支名称
     */
    private String BPLName;

    /**
     * 启用
     */
    private String Disabled;

    /**
     * 别名
     */
    private String AliasName;

    public Integer getOBPLId() {
        return OBPLId;
    }

    public void setOBPLId(Integer OBPLId) {
        this.OBPLId = OBPLId;
    }

    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public String getDisabled() {
        return Disabled;
    }

    public void setDisabled(String disabled) {
        Disabled = disabled;
    }

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String aliasName) {
        AliasName = aliasName;
    }
}
