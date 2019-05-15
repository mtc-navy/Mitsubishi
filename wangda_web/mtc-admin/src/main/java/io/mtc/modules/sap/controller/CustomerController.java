package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.BoundNoEntity;
import io.mtc.modules.sap.entity.CarNoEntity;
import io.mtc.modules.sap.entity.CustomerAmtEntity;
import io.mtc.modules.sap.entity.CustomerEntity;
import io.mtc.modules.sap.service.CustomerService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername();
        //数据类型：A-新增,S-查询
        String dataType = (String) params.get("DataType");
        //过滤条件
        String filterValue = (String) params.get("FilterValue");
        List<CustomerEntity> customerEntityList = customerService.GetList(userName, dataType, filterValue);
        return R.ok().put("customerList", customerEntityList);
    }

    /**
     * 获取车牌号
     */
    @RequestMapping("/car")
    public R getCarNoList(@RequestParam Map<String, Object> params) {
        //客户编号
        String cardCode = (String) params.get("CardCode");
        //车牌号
        String carNo = (String) params.get("CarNo");
        //单据日期
        String taxDate = (String) params.get("TaxDate");
        //销售分支
        String salesBranch = (String) params.get("SalesBranch");
        //提货分支
        String takeBranch = (String) params.get("TakeBranch");

        //提货分支
        List<CarNoEntity> carNoEntityList = customerService.getCarNoList(cardCode, carNo, taxDate,salesBranch,takeBranch);
        return R.ok().put("car", carNoEntityList);
    }

    /**
     * 获取过磅单号
     */
    @RequestMapping("/bound")
    public R getBoundNoList(@RequestParam Map<String, Object> params) {
        //客户编号
        String cardCode = (String) params.get("CardCode");
        //车牌号
        String carNo = (String) params.get("CarNo");
        //单据日期
        String taxDate = (String) params.get("TaxDate");
        List<BoundNoEntity> boundNoEntityList = customerService.getBoundNoList(cardCode, carNo, taxDate);
        return R.ok().put("bound", boundNoEntityList);
    }

    /**
     * 获取往来款、折扣余额、可用金额
     */
    @RequestMapping("/amt")
    public R getCusAmt(@RequestParam Map<String, Object> params) {
        //客户编号
        String cardCode = (String) params.get("CardCode");
        //销售分支
        String bplid = (String) params.get("BPLId");

        List<CustomerAmtEntity> customerAmtEntityList = customerService.getCustomerAmt(bplid, cardCode);
        return R.ok().put("amt", customerAmtEntityList);
    }
}
