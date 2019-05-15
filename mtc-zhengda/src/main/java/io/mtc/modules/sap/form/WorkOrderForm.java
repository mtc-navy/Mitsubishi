package io.mtc.modules.sap.form;

import io.mtc.modules.sap.entity.MtcWRDR1Entity;
import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 提货单Dto
 */
@Data
@ApiModel(value = "提货单Dto")
public class WorkOrderForm {

    @ApiModelProperty(value = "提货单主表")
    private MtcWRDREntity master;

    @ApiModelProperty(value = "提货单子表")
    private List<MtcWRDR1Entity> detail;

}
