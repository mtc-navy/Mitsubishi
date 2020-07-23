package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/notice")
public class SysNoticeShowController extends BaseController {

    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取7天内的通告内容
     *
     * @return
     */
    @RequestMapping("/show")
    public TableDataInfo show() {
        SysNotice sysNotice = new SysNotice();
        List<SysNotice> list = noticeService.selectNoticeList(sysNotice);
        //获取7天前所有的通告内容
        list = list.stream().filter(notice ->
                notice.getCreateTime().compareTo(DateUtils.addDays(DateUtils.getNowDate(), -7)) >= 0
                        && "0".equals(notice.getStatus())).collect(Collectors.toList());
        return getDataTable(list);
    }
}
