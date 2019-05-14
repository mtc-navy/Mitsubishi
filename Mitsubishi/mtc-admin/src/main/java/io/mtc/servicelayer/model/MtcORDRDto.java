package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单信息表")
public class MtcORDRDto extends MtcORDR implements Serializable {
    private List<MtcRDR1> MTC_RDR5Collection;
    private List<MtcRDR2> MTC_RDR1Collection;
    private List<MtcRDR3> MTC_RDR2Collection;
    private List<MtcRDR4> MTC_RDR3Collection;
    private List<MtcRDR5> MTC_RDR4Collection;
    private List<MtcRDR6> MTC_RDR6Collection;
}
