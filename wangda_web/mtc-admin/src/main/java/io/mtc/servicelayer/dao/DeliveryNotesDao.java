package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import org.springframework.stereotype.Component;

/**
 * 销售交货
 *
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/DeliveryNotes")
@Component
public class DeliveryNotesDao extends BaseServiceLayerDao<Order>{
}
