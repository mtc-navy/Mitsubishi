package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 采购订单
 * <p>
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/PurchaseOrders")
@Component
public class PurchaseOrdersDao extends BaseServiceLayerDao<Order> {
}
