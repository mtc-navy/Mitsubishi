package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.BPLEntity;
import io.mtc.modules.sap.service.BPLService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap/bpl")
public class BPLController {

    @Autowired
    private BPLService bplService;

    /**
     * 根据客户获取收货人信息
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //客户编码
        String cardCode = (String) params.get("CardCode");
        //数据类型：S-销售分支,T-提货分支
        String dataType = (String) params.get("DataType");
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername();
        //过滤条件
        String filterValue = (String) params.get("FilterValue");

        List<BPLEntity> bplEntityList = bplService.search(cardCode, dataType, userName, filterValue);
        return R.ok().put("bpl", bplEntityList);
    }

    /**
     * 根据当前用户列出权限范围内分支
     * @return
     */
    @RequestMapping("/listByUser")
    public R listByUser(@RequestParam Map<String, Object> params){
        //过滤条件
        String filterValue = (String)params.get("filterValue");
        List<BPLEntity> bplEntityList = bplService.searchByUser(ShiroUtils.getUserEntity().getUsername(), filterValue);
        return R.ok().put("bpl", bplEntityList);
    }
}
