package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询条件参数
 */
@Data
@ApiModel(value = "查询条件参数")
public class SearchParam {

    @ApiModelProperty(value = "页码")
    private Integer pageIndex;

    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;

    @ApiModelProperty(value = "起始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "客户代码")
    private String cardCode;

    @ApiModelProperty(value = "客户名称")
    private String cardName;

    @ApiModelProperty(value = "状态")
    private String docStatus;

}
