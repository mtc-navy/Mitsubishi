package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售退货单
 *
 * Created by majun on 2018/9/3.
 */
@Path(value = "/Returns")
@Component
public class ReturnsDao extends BaseServiceLayerDao<Order> {
}
