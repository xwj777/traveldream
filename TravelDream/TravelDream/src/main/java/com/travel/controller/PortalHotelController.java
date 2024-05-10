package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Hotel;
import com.travel.pojo.TravelRoute;
import com.travel.pojo.User;
import com.travel.service.HotelService;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-22 14:41
 * @Description: TODO
 */
@Controller
public class PortalHotelController {

    @Autowired
    HotelService hotelService;

    /**
     * 01-分页查询列表
     *
     * @return
     */
    @RequestMapping("portal_hotel_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");//根据新增时间降序
        IPage page = hotelService.page(new Page<Hotel>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Hotel> pagerHelper = new PagerHelper<Hotel>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("val", "portal_hotel_list");
        return "portal/hotelAccommodation";
    }

    /**
     * 02-旅游路线详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("portal_hotel_toview/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        Hotel hotel = hotelService.getById(id);
        model.addAttribute("entity", hotel);
        model.addAttribute("val", "portal_hotel_list");
        return "portal/hotelAccommodationView";
    }

    /**
     * 03-跳转酒店预订页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("user_tohotelOrder/{id}")
    public String tohotelOrder(@PathVariable("id") String id, HttpSession session, Model model) {
        Hotel hotel = hotelService.getById(id);
        User user = (User) session.getAttribute("user");
        model.addAttribute("entity", hotel);
        model.addAttribute("user", user);
        model.addAttribute("val", "portal_hotel_list");
        return "portal/reserve";
    }
}
