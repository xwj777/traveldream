package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.pojo.MainData;
import com.travel.pojo.OrderData;
import com.travel.pojo.Province;
import com.travel.pojo.User;
import com.travel.service.*;
import com.travel.service.impl.StrategyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-24 14:20
 * @Description: TODO
 */
@Controller
public class DataController {

    @Autowired
    TravelRouteService travelRouteService;

    @Autowired
    ScenicSpotService scenicSpotService;

    @Autowired
    HotelService hotelService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    StrategyService strategyService;

    @Autowired
    CarService carService;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    UserService userService;

    @Autowired
    ProvinceService provinceService;

    /**
     * 01-跳转旅游路线数据分析页面
     *
     * @return
     */
    @RequestMapping("/travelRouteData")
    public String totravelRouteData(Model model) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = travelRouteService.listMaps(queryWrapper);

        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        model.addAttribute("count_0",count_0);
        model.addAttribute("count_1",count_1);
        model.addAttribute("count_2",count_2);

        return "data/travelRouteData";
    }

    /**
     * 02-景点数据分析
     * @return
     */
    @GetMapping("/toscenicSpotData")
    public String toscenicSpotData(Model model){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = scenicSpotService.listMaps(queryWrapper);
        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        String datas="["+count_0+", "+count_1+", "+count_2+"]";

        model.addAttribute("datas",datas);

        return "data/scenicSpotData";
    }

    @RequestMapping("tohotelData")
    public String hotelData(Model model){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = hotelService.listMaps(queryWrapper);
        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        String datas="["+count_0+", "+count_1+", "+count_2+"]";

        model.addAttribute("datas",datas);

        return "data/hotelData";

    }

    /**
     * 订单数据分析
     * @return
     */
    @RequestMapping("/toorderData")
    public String toorderData(Model model) throws Exception{
        List<OrderData> orderDatas = userOrderService.getOrderDatas();
        for (OrderData orderData : orderDatas) {
            if("0".equals(orderData.getType().toString())){
                orderData.setName("旅游路线");
            }else if("1".equals(orderData.getType().toString())){
                orderData.setName("旅游景点");
            }else if("2".equals(orderData.getType().toString())){
                orderData.setName("旅游酒店");
            }else if("3".equals(orderData.getType().toString())){
                orderData.setName("旅游车票");
            }else if("4".equals(orderData.getType().toString())){
                orderData.setName("旅游保险");
            }
        }
        //对象转JSON
        ObjectMapper objectMapper=new ObjectMapper();
        String datas = objectMapper.writeValueAsString(orderDatas);
        System.out.println(datas);
        //[{"value":1,"name":null,"type":0},
        // {"value":4,"name":null,"type":1},
        // {"value":5,"name":null,"type":2},
        // {"value":1,"name":null,"type":3},
        // {"value":1,"name":null,"type":4}]
        model.addAttribute("datas",datas);
        return "data/orderData";
    }

    /**
     * 01-跳转旅游路线数据分析页面
     *
     * @return
     */
    @RequestMapping("/tostrategyData")
    public String tostrategyData(Model model) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = strategyService.listMaps(queryWrapper);

        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        String datas="["+count_0+", "+count_1+", "+count_2+"]";

        model.addAttribute("datas",datas);

        return "data/strategyData";
    }

    /**
     * 01-跳转旅游路线数据分析页面
     *
     * @return
     */
    @RequestMapping("/tocarData")
    public String tocarData(Model model) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = carService.listMaps(queryWrapper);

        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        model.addAttribute("count_0",count_0);
        model.addAttribute("count_1",count_1);
        model.addAttribute("count_2",count_2);

        return "data/carData";
    }

    /**
     * 01-保险
     *
     * @return
     */
    @RequestMapping("/toinsuranceData")
    public String toinsuranceData(Model model) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = insuranceService.listMaps(queryWrapper);

        Map<String, Object> map0 = list.get(0);
        Object count_0 = map0.get("count(id)");

        Map<String, Object> map1 = list.get(1);
        Object count_1 = map1.get("count(id)");

        Map<String, Object> map2 = list.get(2);
        Object count_2 = map2.get("count(id)");

        model.addAttribute("count_0",count_0);
        model.addAttribute("count_1",count_1);
        model.addAttribute("count_2",count_2);

        return "data/insuranceData";
    }

    @RequestMapping("/touserData")
    public String touserData(Model model) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.select("count(id),STATE");
        queryWrapper.groupBy("STATE");
        queryWrapper.orderByAsc("STATE");

        List<Map<String,Object>> list = userService.listMaps(queryWrapper);

        Object count_0 =null;
        if(list.size()>0){
            Map<String, Object> map0 = list.get(0);
            count_0 = map0.get("count(id)");
        }else{
            count_0=0;
        }

        Object count_1 =null;
        if(list.size()>1){
            Map<String, Object> map1 = list.get(1);
            count_1 = map1.get("count(id)");
        }else{
            count_1=0;
        }

        model.addAttribute("count_0",count_0);
        model.addAttribute("count_1",count_1);

        return "data/userData";
    }

    /**
     * 中国地图数据分析
     * @return
     */
    @RequestMapping("/tomain")
    public String tomain(Model model) throws Exception{
        List<MainData> mainDataList=new ArrayList<>();
        List<Province> provinces = provinceService.list();

        for (Province province : provinces) {
            long id = province.getId();
            int count = userService.count(new QueryWrapper<User>().eq("PROVINCE", id).eq("DELETE_STATUS", 0));
            MainData data=new MainData(province.getProvince(),count);
            mainDataList.add(data);
        }

        ObjectMapper objectMapper=new ObjectMapper();
        String datas = objectMapper.writeValueAsString(mainDataList);
        System.out.println(datas);

        model.addAttribute("datas",datas);

        return "data/main";
    }
}
