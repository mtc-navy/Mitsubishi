package io.mtc.modules.sap.controller;

import io.mtc.common.utils.R;
import io.mtc.modules.sap.entity.WarehouseEntity;
import io.mtc.modules.sap.service.WarehouseService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sap/whs")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    /**
     * 获取转储源仓库
     * 	基地开票员：从仓库为该分支的成品仓库
     * 	办事处开票员：其用户对应的仓库
     *
     * @param bplid
     * @return
     */
    @RequestMapping("/searchFromWhs")
    public R searchCPByBPL(String bplid, String filterValue) {
        List<WarehouseEntity> warehouseEntityList = null;
        switch (ShiroUtils.getUserEntity().getUserType()) {
            case "1": //基地开票员
                warehouseEntityList = warehouseService.searchCPByBPL(bplid, filterValue);
                break;
            case "2": //办事处开票员
                String userName = ShiroUtils.getUserEntity().getUsername();
                warehouseEntityList = warehouseService.searchByUser(bplid, userName, filterValue);
                break;
        }

        return R.ok().put("whs", warehouseEntityList);
    }

    /**
     * 获取转储目标仓库
     * 	基地开票员：该分支的办事处仓库
     * 	办事处开票员：该分支的其他的仓库（包括办事处仓库和成品仓库）
     *
     * @param bplid
     * @return
     */
    @RequestMapping("/searchToWhs")
    public R searchBSCByBPL(String bplid, String filterValue) {
        List<WarehouseEntity> warehouseEntityList = null;
        switch (ShiroUtils.getUserEntity().getUserType()) {
            case "1": //基地开票员
                warehouseEntityList = warehouseService.searchBSCByBPL(bplid, filterValue);
                break;
            case "2": //办事处开票员
                String userName = ShiroUtils.getUserEntity().getUsername();
                warehouseEntityList = warehouseService.searchCBByUser(bplid, userName, filterValue);
                break;
        }
        return R.ok().put("whs", warehouseEntityList);
    }
}
