package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.CntctPrsnEntity;
import io.mtc.modules.sap.service.CntctPrsnService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/cntctPrsn")
public class CntctPrsnController {

    @Autowired
    private CntctPrsnService cntctPrsnService;

    /**
     * 根据客户获取收货人信息
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //客户编码
        String cardCode = (String) params.get("CardCode");
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername();
        //过滤条件
        String filterValue = (String) params.get("FilterValue");

        List<CntctPrsnEntity> cntctPrsnEntities = cntctPrsnService.search(cardCode, userName, filterValue);
        return R.ok().put("cntctPrsn", cntctPrsnEntities);
    }
}
