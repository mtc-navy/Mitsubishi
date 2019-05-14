package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售订单
 * <p>
 * Created by majun on 2018/9/3.
 */
@Path(value = "/Drafts")
@Component
public class DraftsDao extends BaseServiceLayerDao<Order> {
}
