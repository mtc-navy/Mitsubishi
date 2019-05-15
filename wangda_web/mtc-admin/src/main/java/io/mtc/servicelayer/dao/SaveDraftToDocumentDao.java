package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.DraftToDocument;
import io.mtc.servicelayer.model.PurchaseOrder;
import org.springframework.stereotype.Component;

/**
 * 订单草稿保存为正式单据
 * <p>
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/DraftsService_SaveDraftToDocument")
@Component
public class SaveDraftToDocumentDao extends BaseServiceLayerDao<DraftToDocument> {
}
