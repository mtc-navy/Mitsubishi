package io.mtc.modules.sap.service;

import io.mtc.modules.sap.entity.ItemEntity;
import io.mtc.modules.sap.entity.ItemPriceEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<ItemEntity> search(String cardCode, String userName, String bplid, String docDate, String desOrder, String priceOrder);

    List<ItemPriceEntity> searchPrice(String cardCode, String itemCode, String docDate, String BPLId);

    Map<String, ItemPriceEntity> search(List<String> itemCodes, String cardCode, String docDate, String BPLId);

    List<ItemEntity> searchByWhs(String whsCode);

    ItemEntity info(String bplid, String whsCode, String itemCode);

}
