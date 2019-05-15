package io.mtc.modules.sap.form;

import io.mtc.modules.sap.entity.MtcWUQT1Entity;
import io.mtc.modules.sap.entity.MtcWUQTEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 计划单明细
 */
@Data
@ApiModel(value = "计划单Dto")
public class PlanForm {

    @ApiModelProperty(value = "计划单主表")
    private MtcWUQTEntity master;

    @ApiModelProperty(value = "计划单子表")
    private List<MtcWUQT1Entity> detail;

}
