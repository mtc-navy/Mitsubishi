package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.MtcORDRDto;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售订单
 * <p>
 * Created by majun on 2018/9/3.
 */
@Path(value = "/MTC_ORDR")
@Component
public class MtcORDRDao extends BaseServiceLayerDao<MtcORDRDto> {
}
