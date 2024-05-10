package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.TravelRoute;
import com.travel.service.TravelRouteService;
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
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-18 09:32
 * @Description: TODO
 */
@Controller
public class TravelRouteController {

    @Autowired
    TravelRouteService travelRouteService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("travelRoute_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String title, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(title)){
            queryWrapper.like("TITLE",title);
        }
        IPage page = travelRouteService.page(new Page<TravelRoute>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<TravelRoute> pagerHelper=new PagerHelper<TravelRoute>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title",title); //查询数据回显
        return "travelRoute/travelRouteList";
    }

    /**
     * 02-打开一个新增旅游路线页面
     * @return
     */
    @GetMapping("travelRoute_toadd")
    public String toadd(){
        return "travelRoute/travelRouteAdd";
    }

    /**
     * 03-新增(异步新增)
     * @return
     */
    @ResponseBody
    @PostMapping("travelRoute_add")
    public CommonResult add(TravelRoute travelRoute, HttpSession session){
        //设置当前系统时间
        travelRoute.setAddTime(DateUtils.getNowTime());
        //设置旅游天数
        travelRoute.setDay(DateUtils.diffDays(travelRoute.getStartTime(),travelRoute.getEndTime()));
        //设置新增路线的人(暂时写死，后期从session中获取)
        travelRoute.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+travelRoute);
        return new CommonResult(200,"请求成功",travelRouteService.save(travelRoute));
    }

    /**
     * 04-跳转详情页面
     * @return
     */
    @GetMapping("travelRoute_todetail/{id}")
    public String todetail(@PathVariable("id") String id,Model model){
        TravelRoute travelRoute = travelRouteService.getById(id);
        model.addAttribute("entity",travelRoute);
        return "travelRoute/travelRouteView";
    }

    /**
     * 05-跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("travelRoute_toEdit/{id}")
    public String toEdit(@PathVariable("id") String id,Model model){
        TravelRoute travelRoute = travelRouteService.getById(id);
        model.addAttribute("entity",travelRoute); //回显数据的对象
        return "travelRoute/travelRouteEdit";
    }


    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("travelRoute_update")
    public CommonResult update(TravelRoute travelRoute, HttpSession session) {

        try {
            //对象是数据库中原对象
            TravelRoute old_route = travelRouteService.getById(travelRoute.getId());

            if(!travelRoute.getImgUrl().equals(old_route.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= old_route.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                File f=new File(realPath);
                if(f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }

            //设置当前系统时间为更新事件
            travelRoute.setModifyTime(DateUtils.getNowTime());
            //设置旅游天数
            travelRoute.setDay(DateUtils.diffDays(travelRoute.getStartTime(),travelRoute.getEndTime()));
            //设置新增路线的人(暂时写死，后期从session中获取)
            travelRoute.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+travelRoute);
            return new CommonResult(200,"请求成功",travelRouteService.updateById(travelRoute));
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
    @GetMapping("travelRoute_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        TravelRoute travelRoute = travelRouteService.getById(id);
        travelRoute.setDeleteStatus(1);//删除状态
        travelRouteService.updateById(travelRoute);
        return new CommonResult(200,"请求成功");
    }
}
