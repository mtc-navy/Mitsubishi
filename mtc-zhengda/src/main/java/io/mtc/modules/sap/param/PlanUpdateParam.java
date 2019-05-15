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
public class PlanUpdateParam extends KeyParam {

    @ApiModelProperty(value = "行号")
    private List<PlanDetail> detail;


    @Data
    public class PlanDetail {

        @ApiModelProperty(value = "行号")
        private Integer lineNum;

        @ApiModelProperty(value = "计划数量")
        private BigDecimal quantity;
    }

}
