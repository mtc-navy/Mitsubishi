package io.mtc.modules.sap.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OA审批后参数
 */
@Data
@ApiModel(value = "OA审批后参数")
public class OAAprvParam {

    @ApiModelProperty(value = "单据编号")
    private Long DocEnty;

    @ApiModelProperty(value = "OA流程编号")
    private String OAReqstNum;

    @ApiModelProperty(value = "OA审批日期")
    private String OAApprDate;

    @ApiModelProperty(value = "OA审批意见")
    private String OAPropose;

}
