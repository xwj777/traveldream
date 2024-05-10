package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Hotel;
import com.travel.pojo.TravelRoute;
import com.travel.service.HotelService;
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
 * @CreateTime: 2021-05-22 14:21
 * @Description: TODO
 */
@Controller
public class HotelController {

    @Autowired
    HotelService hotelService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("hotel_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String hotelName, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(hotelName)){
            queryWrapper.like("HOTEL_NAME",hotelName);
        }
        IPage page = hotelService.page(new Page<Hotel>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Hotel> pagerHelper=new PagerHelper<Hotel>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("hotelName",hotelName); //查询数据回显
        return "hotel/hotelList";
    }

    /**
     * 02-打开一个新增旅游路线页面
     * @return
     */
    @GetMapping("hotel_toadd")
    public String toadd(){
        return "hotel/hotelAdd";
    }

    /**
     * 03-新增(异步新增)
     * @return
     */
    @ResponseBody
    @PostMapping("hotel_add")
    public CommonResult add(Hotel hotel, HttpSession session){
        //设置当前系统时间
        hotel.setAddTime(DateUtils.getNowTime());
        //设置新增路线的人(暂时写死，后期从session中获取)
        hotel.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+hotel);
        return new CommonResult(200,"请求成功",hotelService.save(hotel));
    }

    /**
     * 04-跳转详情页面
     * @return
     */
    @GetMapping("hotel_todetail/{id}")
    public String todetail(@PathVariable("id") String id,Model model){
        Hotel hotel = hotelService.getById(id);
        model.addAttribute("entity",hotel);
        return "hotel/hotelView";
    }

    /**
     * 05-跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("hotel_toEdit/{id}")
    public String toEdit(@PathVariable("id") String id,Model model){
        Hotel hotel = hotelService.getById(id);
        model.addAttribute("entity",hotel); //回显数据的对象
        return "hotel/hotelEdit";
    }


    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("hotel_update")
    public CommonResult update(Hotel hotel, HttpSession session) {

        try {
            //对象是数据库中原对象
            Hotel old_hotel = hotelService.getById(hotel.getId());

            if(!hotel.getImgUrl().equals(old_hotel.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= old_hotel.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                File f=new File(realPath);
                if(f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }

            //设置当前系统时间为更新事件
            hotel.setModifyTime(DateUtils.getNowTime());
            //设置新增路线的人(暂时写死，后期从session中获取)
            hotel.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+hotel);
            return new CommonResult(200,"请求成功",hotelService.updateById(hotel));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(500,"请求失败");
        }
    }

    /**
     * 07-删除旅游路线
     * @param id
     * @return
     */
    @GetMapping("hotel_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        Hotel hotel = hotelService.getById(id);
        hotel.setDeleteStatus(1L);//删除状态
        hotelService.updateById(hotel);
        return new CommonResult(200,"请求成功");
    }
}
