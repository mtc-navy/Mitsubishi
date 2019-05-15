package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 明细主键参数
 */
@Data
@ApiModel(value = "明细主键参数")
public class LineKeyParam extends KeyParam {

    @ApiModelProperty(value = "行号")
    private List<Integer> detail;

}
