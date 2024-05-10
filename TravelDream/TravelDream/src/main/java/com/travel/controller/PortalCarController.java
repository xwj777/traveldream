package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Car;
import com.travel.pojo.TravelRoute;
import com.travel.service.CarService;
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
 * @CreateTime: 2021-05-22 16:07
 * @Description: TODO
 */
@Controller
public class PortalCarController {

    @Autowired
    CarService carService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("portal_car_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = carService.page(new Page<Car>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Car> pagerHelper=new PagerHelper<Car>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("val","portal_car_list");
        return "portal/car";
    }

    /**
     * 02-旅游路线详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_car_toview/{id}")
    public String view(@PathVariable("id") String id, Model model){
        Car car = carService.getById(id);
        model.addAttribute("entity",car);
        model.addAttribute("val","portal_car_list");
        return "portal/carView";
    }
}
