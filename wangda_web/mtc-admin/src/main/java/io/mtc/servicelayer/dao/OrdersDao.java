package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售订单
 *
 * Created by majun on 2018/9/3.
 */
@Path(value = "/Orders")
@Component
public class OrdersDao extends BaseServiceLayerDao<Order> {
}
