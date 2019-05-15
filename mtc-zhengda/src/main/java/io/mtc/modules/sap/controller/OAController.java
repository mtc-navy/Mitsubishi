package io.mtc.modules.sap.controller;

import io.mtc.common.annotation.SysLog;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.R;
import io.mtc.modules.sap.param.ItemParam;
import io.mtc.modules.sap.param.OAAprvParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OA接口
 */
@RestController
@RequestMapping("/sap/workorder/")
public class OAController {

    /**
     * 创建提货单后：
     * 1. 若检查到客户不存在固定额度（D），若赊销，则移动端或WEB提货单 每笔在提交时，均触发至OA进行审批；
     * 2. 若检查到客户存在固定额度（D），则根据SAP配置表《授信开单配置》判断在超过三类额度的情况下，是否 触发至OA进行审批；
     *
     * @param itemParam
     * @return
     * @throws RRException
     */
    @PostMapping("oaapply")
    @ApiOperation("I0024-OA审批流创建：向OA提交申请")
    @SysLog("I0024-OA审批流创建")
    public R oaapply(@RequestBody ItemParam itemParam) throws RRException {

        return R.ok();
    }

    @PostMapping("oaapprove")
    @ApiOperation("I0025-OA审批结束状态回写：OA审批意见回写")
    @SysLog("I0025-OA审批结束状态回写")
    public R oaapprove(@RequestBody OAAprvParam oaAprvParam) throws RRException {
        if (oaAprvParam.getDocEnty() == null) {
            return R.error("单据编号不能为空");
        }
        return R.ok();
    }
}
