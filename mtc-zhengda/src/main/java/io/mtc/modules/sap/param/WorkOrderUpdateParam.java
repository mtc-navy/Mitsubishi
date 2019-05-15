package io.mtc.modules.sap.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 修改计划单参数
 */
@Data
@ApiModel(value = "修改计划单参数")
public class WorkOrderUpdateParam extends KeyParam {

    @ApiModelProperty(value = "行号")
    private List<WorkOrderDetail> detail;


    @Data
    public class WorkOrderDetail {

        @ApiModelProperty(value = "行号")
        private Integer lineNum;

        @ApiModelProperty(value = "提货数量")
        private BigDecimal quantity;

        @ApiModelProperty(value = "车牌号")
        private String carNum;
    }

}
