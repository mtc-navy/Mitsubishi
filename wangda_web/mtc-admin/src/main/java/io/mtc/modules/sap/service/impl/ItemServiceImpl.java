package io.mtc.modules.sap.service.impl;

import io.mtc.common.utils.ObjectUtil;
import io.mtc.modules.sap.dao.ItemDao;
import io.mtc.modules.sap.dao.ItemPriceDao;
import io.mtc.modules.sap.entity.ItemEntity;
import io.mtc.modules.sap.entity.ItemPriceEntity;
import io.mtc.modules.sap.service.ItemService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemPriceDao itemPriceDao;

    @Override
    public List<ItemEntity> search(String cardCode, String userName, String bplid, String docDate, String desOrder, String priceOrder) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        userName = ObjectUtil.nullToEmpty(userName).trim();
        bplid = ObjectUtil.nullToEmpty(bplid).trim();
        docDate = ObjectUtil.nullToEmpty(docDate).trim();
        desOrder = ObjectUtil.nullToEmpty(desOrder).trim();
        priceOrder = ObjectUtil.nullToEmpty(priceOrder).trim();

        List<ItemEntity> itemEntityList = itemDao.search(cardCode, userName, bplid, docDate, desOrder, priceOrder);
        return itemEntityList;
    }

    @Override
    public List<ItemPriceEntity> searchPrice(String cardCode, String itemCode, String docDate, String bplid) {
        cardCode = ObjectUtil.nullToEmpty(cardCode).trim();
        itemCode = ObjectUtil.nullToEmpty(itemCode).trim();
        docDate = ObjectUtil.nullToEmpty(docDate).trim();
        bplid = ObjectUtil.nullToEmpty(bplid).trim();

        List<ItemPriceEntity> itemPriceEntities = itemPriceDao.search(cardCode, itemCode, docDate, bplid);
        ItemPriceEntity entity;
        if (CollectionUtils.isEmpty(itemPriceEntities) || itemPriceEntities.size() < 1) {
            entity = new ItemPriceEntity(new BigDecimal(0));
        } else {
            entity = itemPriceEntities.get(0);
        }
       /* String supRegName = itemPriceDao.getSupRegName(cardCode,itemCode,bplid);
        entity.setItemCode(itemCode);
        entity.setRegSupName(supRegName);*/

        return Arrays.asList(entity);
    }

    @Override
    public Map<String, ItemPriceEntity> search(List<String> itemCodes, String cardCode, String docDate, String bplid) {
        if (CollectionUtils.isEmpty(itemCodes)) {
            return Collections.EMPTY_MAP;
        }

        Map<String, ItemPriceEntity> map = new HashMap<>();
        for (String itemCode : itemCodes) {
            List<ItemPriceEntity> itemPriceEntities = this.searchPrice(cardCode, itemCode, docDate, bplid);
            ItemPriceEntity itemPriceEntity = itemPriceEntities.get(0);
            map.put(itemCode, itemPriceEntity);
        }

        return map;
    }

    @Override
    public List<ItemEntity> searchByWhs(String whsCode) {
        //判断是否办事处仓库(

        return itemDao.searchByWhs(whsCode, ShiroUtils.getUserEntity().getUsername());
    }

    @Override
    public ItemEntity info(String bplid, String whsCode, String itemCode) {
        return itemDao.info(bplid, whsCode, itemCode);
    }
}
