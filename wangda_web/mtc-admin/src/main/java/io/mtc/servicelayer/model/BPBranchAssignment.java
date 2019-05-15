package io.mtc.servicelayer.model;

import java.io.Serializable;

/**
 * 业务伙伴所属分支
 * Created by navy.jiang on 2018/9/3.
 */
public class BPBranchAssignment implements Serializable {

    private static final long serialVersionUID = 1415394455011548627L;
    /**
     * 业务伙伴代码
     */
    private String BPCode;

    /**
     * 分支编号
     */
    private String BPLID;

    public String getBPCode() {
        return BPCode;
    }

    public void setBPCode(String BPCode) {
        this.BPCode = BPCode;
    }

    public String getBPLID() {
        return BPLID;
    }

    public void setBPLID(String BPLID) {
        this.BPLID = BPLID;
    }
}
