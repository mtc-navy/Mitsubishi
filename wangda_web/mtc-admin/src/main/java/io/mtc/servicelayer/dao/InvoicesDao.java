package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售发票
 * <p>
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/Invoices")
@Component
public class InvoicesDao extends BaseServiceLayerDao<Order> {
}
