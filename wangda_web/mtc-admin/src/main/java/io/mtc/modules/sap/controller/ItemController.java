package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.ItemEntity;
import io.mtc.modules.sap.entity.ItemPriceEntity;
import io.mtc.modules.sap.service.ItemService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据客户的可销清单获取物料信息
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //客户编码
        String cardCode = (String) params.get("CardCode");
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername();
        //分支
        String bplid = (String) params.get("BPLId");

        //单据日期
        String docDate = (String) params.get("DocDate");

        //订料单号
        String desOrder = (String) params.get("DesOrder");

        //订料单号
        String priceOrder = (String) params.get("PriceOrder");

        List<ItemEntity> itemEntityList = itemService.search(cardCode, userName, bplid, docDate, desOrder, priceOrder);
        return R.ok().put("item", itemEntityList);
    }

    /**
     * 获取物料的出厂价
     */
    @RequestMapping("/price")
    public R price(@RequestParam Map<String, Object> params) {
        //客户编码
        String cardCode = (String) params.get("CardCode");
        //物料编码
        String itemCode = (String) params.get("ItemCode");
        //单据日期
        String docDate = (String) params.get("DocDate");
        //分支
        String bplid = (String) params.get("BPLId");

        List<ItemPriceEntity> itemPriceEntities = itemService.searchPrice(cardCode, itemCode, docDate, bplid);
        return R.ok().put("price", itemPriceEntities);
    }

    /**
     * 获取多个物料出厂价
     *
     * @param CardCode
     * @param DocDate
     * @param BPLId
     * @param itemCodes
     * @return
     */
    @RequestMapping("/priceAll")
    public R priceAll(@RequestParam String CardCode, @RequestParam String DocDate, @RequestParam String BPLId, @RequestParam("itemCodes") String itemCodes) {

        Map<String, ItemPriceEntity> result = itemService.search(Arrays.asList(itemCodes.split(",")), CardCode, DocDate, BPLId);

        return R.ok().put("price", result);
    }

    /**
     * 根据仓库得到有库存的可销清单
     *
     * @param whsCode
     * @return
     */
    @RequestMapping("listByWhs")
    public R listByWhs(String whsCode) {
        List<ItemEntity> result = itemService.searchByWhs(whsCode);
        return R.ok().put("item", result);
    }


    /**
     * 得到单个物料信息
     *
     * @param itemCode
     * @return
     */
    @RequestMapping("info")
    public R info(String bplid, String whsCode, String itemCode) {
        ItemEntity result = itemService.info(bplid, whsCode, itemCode);
        return R.ok().put("item", result);
    }
}
