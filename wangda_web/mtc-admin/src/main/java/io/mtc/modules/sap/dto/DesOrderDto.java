package io.mtc.modules.sap.dto;

import io.mtc.modules.sap.entity.MtcSdDLD1;
import io.mtc.modules.sap.entity.MtcSdDLD2;
import io.mtc.modules.sap.entity.MtcSdODLD;

import java.util.List;

/**
 * 订料单Dto
 */
public class DesOrderDto {

    /**
     * 订料单主表
     */
    private MtcSdODLD mtcSdODLD;

    /**
     * 订料单明细表
     */
    private List<MtcSdDLD1> mtcSdDLD1List;

    /**
     * 订料单收款明细
     */
    private List<MtcSdDLD2> mtcSdDLD2List;

    public MtcSdODLD getMtcSdODLD() {
        return mtcSdODLD;
    }

    public void setMtcSdODLD(MtcSdODLD mtcSdODLD) {
        this.mtcSdODLD = mtcSdODLD;
    }

    public List<MtcSdDLD1> getMtcSdDLD1List() {
        return mtcSdDLD1List;
    }

    public void setMtcSdDLD1List(List<MtcSdDLD1> mtcSdDLD1List) {
        this.mtcSdDLD1List = mtcSdDLD1List;
    }

    public List<MtcSdDLD2> getMtcSdDLD2List() {
        return mtcSdDLD2List;
    }

    public void setMtcSdDLD2List(List<MtcSdDLD2> mtcSdDLD2List) {
        this.mtcSdDLD2List = mtcSdDLD2List;
    }
}
