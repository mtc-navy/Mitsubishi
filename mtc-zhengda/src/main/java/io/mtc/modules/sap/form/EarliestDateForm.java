package io.mtc.modules.sap.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料列表参数
 */
@Data
@ApiModel(value = "提货日期列表")
public class EarliestDateForm {

    @ApiModelProperty(value = "提货日期")
    private String earliestDate;

}
