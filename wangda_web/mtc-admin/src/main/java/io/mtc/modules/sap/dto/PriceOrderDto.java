package io.mtc.modules.sap.dto;

import io.mtc.modules.sap.entity.MtcSdBJD1;
import io.mtc.modules.sap.entity.MtcSdBJD2;
import io.mtc.modules.sap.entity.MtcSdOBJD;

import java.util.List;

/**
 * 保价单Dto
 */
public class PriceOrderDto {

    /**
     * 保价单主表
     */
    private MtcSdOBJD mtcSdOBJD;

    /**
     * 保价单子表
     */
    private List<MtcSdBJD1> mtcSdBJD1List;

    /**
     * 保价单收款单表
     */
    private List<MtcSdBJD2> mtcSdBJD2List;

    public MtcSdOBJD getMtcSdOBJD() {
        return mtcSdOBJD;
    }

    public void setMtcSdOBJD(MtcSdOBJD mtcSdOBJD) {
        this.mtcSdOBJD = mtcSdOBJD;
    }

    public List<MtcSdBJD1> getMtcSdBJD1List() {
        return mtcSdBJD1List;
    }

    public void setMtcSdBJD1List(List<MtcSdBJD1> mtcSdBJD1List) {
        this.mtcSdBJD1List = mtcSdBJD1List;
    }

    public List<MtcSdBJD2> getMtcSdBJD2List() {
        return mtcSdBJD2List;
    }

    public void setMtcSdBJD2List(List<MtcSdBJD2> mtcSdBJD2List) {
        this.mtcSdBJD2List = mtcSdBJD2List;
    }
}
