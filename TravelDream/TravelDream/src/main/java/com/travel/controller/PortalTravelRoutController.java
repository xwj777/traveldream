package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.TravelRoute;
import com.travel.service.TravelRouteService;
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
 * @CreateTime: 2021-05-18 15:39
 * @Description: 门户的旅游路线列表
 */
@Controller
public class PortalTravelRoutController {

    @Autowired
    TravelRouteService travelRouteService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("portal_travelRoute_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = travelRouteService.page(new Page<TravelRoute>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<TravelRoute> pagerHelper=new PagerHelper<TravelRoute>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("val","portal_travelRoute_list");
        return "portal/travelRoute";
    }

    /**
     * 02-旅游路线详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_travelRoute_toview/{id}")
    public String view(@PathVariable("id") String id,Model model){
        TravelRoute travelRoute = travelRouteService.getById(id);
        model.addAttribute("entity",travelRoute);
        return "portal/travelRouteView";
    }

}
