package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.ScenicSpot;
import com.travel.pojo.Strategy;
import com.travel.service.StrategyService;
import com.travel.utils.CommonResult;
import com.travel.utils.DateUtils;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-24 10:23
 * @Description: TODO
 */
@Controller
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    /**
     * 01-分页查询列表
     *
     * @return
     */
    @RequestMapping("strategy_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String title, Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");
        if (!"".equals(title)) {
            queryWrapper.like("TITLE", title);
        }
        IPage page = strategyService.page(new Page<Strategy>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Strategy> pagerHelper = new PagerHelper<Strategy>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("title", title); //查询数据回显
        return "strategy/strategyList";
    }

    /**
     * 02-跳转到景点新增页面
     *
     * @return
     */
    @GetMapping("strategy_toAdd")
    public String toAdd() {
        return "strategy/strategyAdd";
    }

    /**
     * 03-新增景点
     *
     * @return
     */
    @PostMapping("strategy_Add")
    @ResponseBody
    public CommonResult add(Strategy strategy) {
        strategy.setAddTime(DateUtils.getNowTime());
        //TODO:后期项目完成之后，改成Session中获取当前登录用户ID
        strategy.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        strategy.setDeleteStatus(0L);
        return new CommonResult(200, "请求成功", strategyService.save(strategy));
    }

    /**
     * 04-跳转详情页面
     *
     * @param id
     * @return
     */
    @GetMapping("strategy_View/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        model.addAttribute("entity", strategyService.getById(id));
        return "strategy/strategyView";
    }

    /**
     * 05-跳转更新页面
     *
     * @param id
     * @return
     */
    @GetMapping("strategy_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id, Model model) {
        model.addAttribute("entity", strategyService.getById(id));
        return "strategy/strategyEdit";
    }

    /**
     * 06-更新(异步更新)
     *
     * @return
     */
    @ResponseBody
    @PostMapping("strategy_update")
    public CommonResult update(Strategy strategy, HttpSession session) {
        try {
            //对象是数据库中原对象
            Strategy old_strategy = strategyService.getById(strategy.getId());

            if (!strategy.getImgUrl().equals(old_strategy.getImgUrl())) {
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath = old_strategy.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath = realPath.substring(1, realPath.length()) + "static" + filePath;
                File f = new File(realPath);
                if (f.exists()) {
                    f.delete();
                    System.out.println("删除了老的图片,地址是：" + realPath);
                }
            }
            //设置当前系统时间为更新事件
            strategy.setModifyTime(DateUtils.getNowTime());
            //TODO:设置新增路线的人(暂时写死，后期从session中获取)
            strategy.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:" + strategy);
            return new CommonResult(200, "请求成功", strategyService.updateById(strategy));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(500, "请求失败");
        }
    }

    /**
     * 07-删除景点
     *
     * @param id
     * @return
     */
    @GetMapping("strategy_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id) {
        //TODO:后期需要补代码判断这个景点是否在使用用，如果在使用中提示暂时不能删除
        Strategy strategy = strategyService.getById(id);
        strategy.setDeleteStatus(1L);//删除状态
        strategyService.updateById(strategy);
        return new CommonResult(200, "请求成功");
    }

}
