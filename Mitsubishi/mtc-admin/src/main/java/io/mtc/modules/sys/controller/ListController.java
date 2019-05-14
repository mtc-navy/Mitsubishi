package io.mtc.modules.sys.controller;

import io.mtc.common.exception.RRException;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.R;
import io.mtc.modules.sys.entity.DropListEntity;
import io.mtc.modules.sys.service.ListService;
import io.mtc.modules.sys.service.SysDictService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通用列表接口
 */
@RestController
@RequestMapping("/sys")
public class ListController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private ListService listService;

    /**
     * 通用列表界面查询（分页）
     *
     * @param listType
     * @param deptCode
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    @RequestMapping("/list/{listType}")
    public R pageList(@PathVariable("listType") String listType, String deptCode, Integer pageIndex, Integer pageSize, String param) throws Exception {
        //获取列表存储过程名
        Map<String, Object> params = new HashMap<>();
        params.put("type", "list_type");
        params.put("code", listType);
        params.put("limit", "1");
        PageUtils page = sysDictService.queryPageWithCode(params);
        if (page.getTotalCount() < 1) {
            throw new RRException("功能尚未配置完成，请联系管理员");
        }

        String loginUserName = ShiroUtils.getUserEntity().getUsername();
        PageUtils result = listService.queryList(page, deptCode, loginUserName, pageIndex, pageSize, param);
        return R.ok().put("page", result);
    }

    /**
     * 通用下拉方法
     *
     * @return
     */
    @RequestMapping("/droplist/{listType}")
    public R dropList(@PathVariable("listType") String listType, String param) throws Exception {
        //获取列表存储过程名
        Map<String, Object> params = new HashMap<>();
        params.put("type", "drop_list");
        params.put("code", listType);
        params.put("limit", "1");
        PageUtils page = sysDictService.queryPageWithCode(params);
        if (page.getTotalCount() < 1) {
            throw new RRException("功能尚未配置完成，请联系管理员");
        }

        String loginUserName = ShiroUtils.getUserEntity().getUsername();
        List<DropListEntity> dropListEntityList = listService.queryList(page, loginUserName,param);
        return R.ok().put("data", dropListEntityList);
    }
}
