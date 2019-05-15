package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.IncomingPayments;
import org.springframework.stereotype.Component;

/**
 * 收款单
 */
@Path(value = "/IncomingPayments")
@Component
public class IncomingPaymentsDao extends BaseServiceLayerDao<IncomingPayments> {
}
