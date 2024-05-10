package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.ScenicSpot;
import com.travel.pojo.TravelRoute;
import com.travel.service.ScenicSpotService;
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
 * @CreateTime: 2021-05-19 10:51
 * @Description: TODO
 */
@Controller
public class PortalScenicSpotController {

    @Autowired
    ScenicSpotService scenicSpotService;

    /**
     * 02-前台列表景点展示
     * @param pageNumber
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("portal_scenicSpot_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = scenicSpotService.page(new Page<ScenicSpot>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper<ScenicSpot>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("val", "portal_scenicSpot_list");
        return "portal/travelSpot";
    }

    /**
     * 02-前台跳转景点详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_scenicSpot_view/{id}")
    public String view(@PathVariable("id") String id,Model model){
        ScenicSpot scenicSpot = scenicSpotService.getById(id);
        model.addAttribute("entity",scenicSpot);
        return "portal/travelSpotView";
    }
}
