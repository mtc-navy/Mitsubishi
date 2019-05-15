package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.PaymentEntity;
import io.mtc.modules.sap.service.PayDiscService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/paydisc")
public class PayDiscController {

    @Autowired
    private PayDiscService payDiscService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //客户编码
        String cardCode = (String) params.get("CardCode");
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername();
        //过滤条件
        String filterValue = (String) params.get("FilterValue");
        //销售分支
        String bplid = (String) params.get("BPLId");

        //获取收款信息
        List<PaymentEntity> paymentEntities = payDiscService.searchPay(cardCode, userName, bplid, filterValue);

        return R.ok().put("pay", paymentEntities);
    }

}
