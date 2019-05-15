package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.dto.StockTransferDto;
import io.mtc.modules.sap.service.StockTransferService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("/sap/stocktransfer")
public class StockTransferController {

    @Autowired
    private StockTransferService stockTransferService;

    /**
     * 查询转储列表(包括申请)
     */
    @RequiresPermissions("sys:stocktransfer:list")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws Exception {
        PageUtils page = stockTransferService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 修改界面取数
     *
     * @param docEntry
     * @param status
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:stocktransfer:info")
    public R info(Long docEntry, String status) throws Exception {
        StockTransferDto dto = stockTransferService.info(docEntry, status);
        return R.ok().put("result", dto);
    }

    /**
     * 取消
     *
     * @param docEntry
     * @param status
     * @return
     */
    @SysLog("取消转储")
    @RequestMapping("/cancel")
    @RequiresPermissions("sys:stocktransfer:cancel")
    public R cancel(Long docEntry, String status) throws Exception {
        stockTransferService.cancel(docEntry, status);
        return R.ok();
    }

    /**
     * 新增转储申请
     *
     * @param stockTransferDto
     * @return
     * @throws Exception
     */
    @SysLog("新增转储申请")
    @RequiresPermissions("sys:stocktransfer:saverequest")
    @RequestMapping("/addRequest")
    public R addRequest(@RequestBody StockTransferDto stockTransferDto) throws Exception {
        BigInteger docEntry = stockTransferService.save(stockTransferDto, true, false);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 修改转储
     *
     * @param stockTransferDto
     * @return
     * @throws Exception
     */
    @SysLog("修改转储申请")
    @RequestMapping("/editRequest")
    @RequiresPermissions("sys:stocktransfer:update")
    public R editRequest(@RequestBody StockTransferDto stockTransferDto) throws Exception {
        BigInteger docEntry = stockTransferService.save(stockTransferDto, true, true);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 添加转储
     *
     * @param stockTransferDto
     * @return
     * @throws Exception
     */
    @SysLog("添加转储")
    @RequestMapping("/add")
    @RequiresPermissions("sys:stocktransfer:save")
    public R add(@RequestBody StockTransferDto stockTransferDto) throws Exception {
        BigInteger docEntry = stockTransferService.save(stockTransferDto, false, false);
        return R.ok().put("docEntry", docEntry);
    }

    /**
     * 修改转储
     *
     * @param stockTransferDto
     * @return
     * @throws Exception
     */
    @SysLog("修改转储")
    @RequestMapping("/edit")
    @RequiresPermissions("sys:stocktransfer:update")
    public R edit(@RequestBody StockTransferDto stockTransferDto) throws Exception {
        BigInteger docEntry = stockTransferService.save(stockTransferDto, false, true);
        return R.ok().put("docEntry", docEntry);
    }

}
