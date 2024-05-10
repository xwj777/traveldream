package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.*;
import com.travel.service.*;
import com.travel.utils.CommonResult;
import com.travel.utils.DateUtils;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-21 14:38
 * @Description: 会员订单模块
 */
@Controller
public class PortalUserOrderController {

    @Autowired
    UserOrderService userOrderService;

    /**
     * 旅游路线
     */
    @Autowired
    TravelRouteService travelRouteService;

    /**
     * 景点
     */
    @Autowired
    ScenicSpotService scenicSpotService;

    /**
     * 酒店
     */
    @Autowired
    HotelService hotelService;

    /**
     * 车票
     */
    @Autowired
    CarService carService;


    /**
     *  保险
     */
    @Autowired
    InsuranceService insuranceService;

    /**
     * 01-尽量通用  下订单
     * @param id
     * @return
     */
    @RequestMapping("user_createOrder")
    public String createOrder(String id, Long product_type, HttpSession session, Model model) {
        String viewName="";
        try {
            User user = (User) session.getAttribute("user");
            UserOrder userOrder = new UserOrder();
            userOrder.setAddUserId(user.getId());
            userOrder.setAddTime(DateUtils.getNowTime());
            userOrder.setDeleteStatus(0L);
            userOrder.setUserId(user.getId());
            userOrder.setUserName(user.getUserName());
            userOrder.setProductId(id);
            userOrder.setProductType(product_type);
            userOrder.setState(0L);
            userOrder.setOrderCode(DateUtils.getOrderId());
            userOrder.setOrderTime(DateUtils.getNowTime());
            userOrder.setLinkTel(user.getLinkTel());
            userOrder.setIcCode(user.getIcCode());
            if (product_type == 0) {
                //旅游路线
                TravelRoute travelRoute = travelRouteService.getById(id);
                userOrder.setProductName(travelRoute.getTitle());
                userOrder.setFee(travelRoute.getPrice());
                userOrder.setSetoffTime(travelRoute.getStartTime());
                userOrder.setImgUrl(travelRoute.getImgUrl());
                model.addAttribute("entity",travelRoute);
                viewName="portal/travelRouteView";
            } else if (product_type == 1) {
                //旅游景点
                ScenicSpot scenicSpot = scenicSpotService.getById(id);
                userOrder.setProductName(scenicSpot.getSpotName());
                userOrder.setFee(scenicSpot.getTicketsMessage());
                userOrder.setSetoffTime(scenicSpot.getOpenTime());
                userOrder.setImgUrl(scenicSpot.getImgUrl());
                model.addAttribute("entity",scenicSpot);
                viewName="portal/travelSpotView";
            } else if (product_type == 3){
                //车票
                Car car = carService.getById(id);
                userOrder.setProductName(car.getTitle());
                userOrder.setFee(car.getPrice());
                userOrder.setSetoffTime(car.getStartDateAndTime());
                userOrder.setImgUrl(car.getImgUrl());
                model.addAttribute("entity",car);
                viewName="portal/carView";
            }else if(product_type == 4) {
                //保险
                Insurance insurance = insuranceService.getById(id);

                userOrder.setProductName(insurance.getTitle());
                userOrder.setFee(insurance.getPrice());
                userOrder.setSetoffTime(DateUtils.getNowTime());
                userOrder.setImgUrl(insurance.getImgUrl());
                model.addAttribute("entity",insurance);
                viewName="portal/insuranceView";
            }
            userOrderService.save(userOrder);
            model.addAttribute("msg","预定成功，请前往会员中心-我的订单查看订单");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg","预定异常");
        }
        return viewName;
    }

    /**
     * 酒店预订
     * @return
     */
    @RequestMapping("createHotelOrder")
    public String createHotelOrder(String hotelId,String name,
                                   String linkTel,String icCode,Long peopleCount,String setoffTime,String requirement, HttpSession session, Model model){
        Hotel hotel = hotelService.getById(hotelId);
        try {
            User user = (User) session.getAttribute("user");

            UserOrder userOrder = new UserOrder();
            userOrder.setAddUserId(user.getId());
            userOrder.setAddTime(DateUtils.getNowTime());
            userOrder.setDeleteStatus(0L);
            userOrder.setUserId(user.getId());
            userOrder.setUserName(name);
            userOrder.setProductId(hotelId);
            userOrder.setProductType(2L);
            userOrder.setState(0L);
            userOrder.setOrderCode(DateUtils.getOrderId());
            userOrder.setOrderTime(DateUtils.getNowTime());
            userOrder.setLinkTel(linkTel);
            userOrder.setIcCode(icCode);

            userOrder.setProductName(hotel.getHotelName());
            userOrder.setFee(hotel.getPrice());
            userOrder.setSetoffTime(setoffTime);
            userOrder.setImgUrl(hotel.getImgUrl());
            userOrder.setPeopleCount(peopleCount);
            userOrder.setRequirement(requirement);

            userOrderService.save(userOrder);
            model.addAttribute("msg","预定成功，请前往会员中心-我的订单查看订单");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg","预定异常");
        } finally {
        }
        model.addAttribute("entity",hotel);
        return "portal/hotelAccommodationView";
    }


    /**
     * 02-分页查询我的订单
     * @return
     */
    @RequestMapping("user_myorderlist")
    public String myorderlist(@RequestParam(defaultValue = "1") Long pageNumber,
                              @RequestParam(defaultValue = "7") Long pageSize, HttpSession session, Model model){
        //当前用户信息
        User user= (User) session.getAttribute("user");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        queryWrapper.eq("USER_ID",user.getId());
        IPage page = userOrderService.page(new Page<UserOrder>(pageNumber, pageSize), queryWrapper);

        //封装工具类
        PagerHelper<UserOrder> pagerHelper=new PagerHelper<UserOrder>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/myOrder";

    }

    /**
     * 会员取消订单
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("user_cancelOrder/{id}/{pageNumber}")
    public String user_cancelOrder(@PathVariable("id") String id,@PathVariable("pageNumber") Long pageNumber,HttpSession session,Model model){
        User user= (User) session.getAttribute("user");
        UserOrder userOrder = userOrderService.getById(id);
        userOrder.setState(2L);//取消订单
        userOrderService.updateById(userOrder);

        //当前用户信息
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        queryWrapper.eq("USER_ID",user.getId());
        IPage page = userOrderService.page(new Page<UserOrder>(pageNumber, 7), queryWrapper);

        //封装工具类
        PagerHelper<UserOrder> pagerHelper=new PagerHelper<UserOrder>(pageNumber,7L,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/myOrder";
    }
}


