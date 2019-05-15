package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Order;
import io.mtc.servicelayer.model.TransferRequest;
import org.springframework.stereotype.Component;

/**
 * 转储
 *
 * Created by andy.xie on 2018/12/28.
 */
@Path(value = "/InventoryTransferRequests")
@Component
public class InventoryTransferRequestsDao extends BaseServiceLayerDao<TransferRequest> {
}
