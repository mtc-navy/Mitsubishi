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

    @RequestMapping("/list/orderDeliveryList")
    public String orderDeliveryList(){

        return "{\"msg\":\"success\",\"code\":0,\"page\":{\"totalCount\":33,\"pageSize\":10,\"totalPage\":4,\"currPage\":1,\"fields\":[\"DOCENTRY\",\"ODLNDocEntry\",\"分支\",\"开票单号\",\"交货单号\",\"提货单号\",\"客户代码\",\"客户名称\",\"过磅单号\",\"车牌号\",\"运输方式\",\"业务类型\",\"司机\",\"交货日期\",\"状态\"],\"lengths\":[\"10\",\"10\",\"80\",\"110\",\"110\",\"160\",\"90\",\"120\",\"80\",\"80\",\"90\",\"90\",\"90\",\"90\",\"90\"],\"list\":[{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068483,\"过磅单号\":null,\"客户代码\":\"CA005705\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"邓天琴\",\"DOCENTRY\":69694,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068482,\"过磅单号\":null,\"客户代码\":\"CA005748\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"杨志凤\",\"DOCENTRY\":69693,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068481,\"过磅单号\":null,\"客户代码\":\"CA012122\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"许尚坤\",\"DOCENTRY\":69692,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"徐文彬\",\"开票单号\":1010068480,\"过磅单号\":\"184\",\"客户代码\":\"CA000035\",\"业务类型\":\"工厂司机拉货\",\"车牌号\":\"川AB3089\",\"客户名称\":\"何建安\",\"DOCENTRY\":69691,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":\"代运\"},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068478,\"过磅单号\":null,\"客户代码\":\"CA009269\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"植爱秋\",\"DOCENTRY\":69689,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068477,\"过磅单号\":null,\"客户代码\":\"CA009161\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"罗长珍\",\"DOCENTRY\":69688,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068476,\"过磅单号\":null,\"客户代码\":\"CA009237\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"杨维良\",\"DOCENTRY\":69687,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068475,\"过磅单号\":null,\"客户代码\":\"CA009096\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"黄太清\",\"DOCENTRY\":69686,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068474,\"过磅单号\":null,\"客户代码\":\"CA003544\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"陈俊英\",\"DOCENTRY\":69685,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null},{\"提货单号\":null,\"分支\":\"崇州旺达\",\"交货日期\":\"2019-05-08\",\"状态\":\"未交\",\"司机\":\"\",\"开票单号\":1010068473,\"过磅单号\":null,\"客户代码\":\"CA009187\",\"业务类型\":\"客户拉货\",\"车牌号\":null,\"客户名称\":\"左跃富（王太芳）\",\"DOCENTRY\":69684,\"ODLNDocEntry\":null,\"交货单号\":null,\"运输方式\":null}]}}";
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
