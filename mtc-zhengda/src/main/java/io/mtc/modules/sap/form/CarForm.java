package io.mtc.modules.sap.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车牌信息
 */
@Data
@ApiModel(value = "车牌信息列表")
public class CarForm {

    @ApiModelProperty(value = "车牌号")
    private String carNum;

    @ApiModelProperty(value = "司机")
    private String driver;

    @ApiModelProperty(value = "运输商编码")
    private String agentCode;

    @ApiModelProperty(value = "运输商名称")
    private String agentName;

}
