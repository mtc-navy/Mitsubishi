package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
 * 车型配置信息
 */
@Data
@ApiModel(value = "车型配置信息")
public class MtcForkCfgDto extends MtcForkCfg implements Serializable {
    private List<MtcForkCfg1> MTC_FORKCFG1Collection;
    private List<MtcForkCfg2> MTC_FORKCFG2Collection;
    private List<MtcForkCfg3> MTC_FORKCFG3Collection;
    private List<MtcForkCfg4> MTC_FORKCFG4Collection;
    private List<MtcForkCfg5> MTC_FORKCFG5Collection;
    private List<MtcForkCfg6> MTC_FORKCFG6Collection;
    private List<MtcForkCfg7> MTC_FORKCFG7Collection;
}
