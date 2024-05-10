package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Insurance;
import com.travel.pojo.ScenicSpot;
import com.travel.service.InsuranceService;
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
 * @CreateTime: 2021-05-24 09:49
 * @Description: TODO
 */
@Controller
public class PortalInsuranceController {

    @Autowired
    InsuranceService insuranceService;

    /**
     * 02-前台列表景点展示
     * @param pageNumber
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("portal_insurance_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = insuranceService.page(new Page<Insurance>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Insurance> pagerHelper = new PagerHelper<Insurance>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("val", "portal_insurance_list");
        return "portal/insurance";
    }

    /**
     * 02-前台跳转景点详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_insurance_view/{id}")
    public String view(@PathVariable("id") String id, Model model){
        Insurance insurance = insuranceService.getById(id);
        model.addAttribute("entity",insurance);
        return "portal/insuranceView";
    }
}
