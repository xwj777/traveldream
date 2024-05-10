package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.ScenicSpot;
import com.travel.pojo.Strategy;
import com.travel.service.StrategyService;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-24 11:04
 * @Description: TODO
 */
@Controller
public class PortalStrategyController {

    @Autowired
    StrategyService strategyService;

    /**
     * 02-前台列表景点展示
     * @param pageNumber
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("portal_strategy_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = strategyService.page(new Page<Strategy>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Strategy> pagerHelper = new PagerHelper<Strategy>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("val", "portal_strategy_list");
        return "portal/strategy";
    }

    /**
     * 02-前台跳转景点详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_strategy_view/{id}")
    public String view(@PathVariable("id") String id, Model model){
        Strategy strategy = strategyService.getById(id);
        model.addAttribute("entity",strategy);
        model.addAttribute("val", "portal_strategy_list");
        return "portal/strategyView";
    }
}
