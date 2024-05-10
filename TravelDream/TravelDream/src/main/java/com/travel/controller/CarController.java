package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Car;
import com.travel.pojo.Hotel;
import com.travel.service.CarService;
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
 * @CreateTime: 2021-05-22 15:49
 * @Description: TODO
 */
@Controller
public class CarController {

    @Autowired
    CarService carService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("car_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String title, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(title)){
            queryWrapper.like("TITLE",title);
        }
        IPage page = carService.page(new Page<Car>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Car> pagerHelper=new PagerHelper<Car>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title",title); //查询数据回显
        return "car/carList";
    }

    /**
     * 02-打开一个新增旅游路线页面
     * @return
     */
    @GetMapping("car_toadd")
    public String toadd(){
        return "car/carAdd";
    }

    /**
     * 03-新增(异步新增)
     * @return
     */
    @ResponseBody
    @PostMapping("car_add")
    public CommonResult add(Car car, HttpSession session){
        //设置当前系统时间
        car.setAddTime(DateUtils.getNowTime());
        //设置新增路线的人(暂时写死，后期从session中获取)
        car.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+car);
        return new CommonResult(200,"请求成功",carService.save(car));
    }

    /**
     * 04-跳转详情页面
     * @return
     */
    @GetMapping("car_todetail/{id}")
    public String todetail(@PathVariable("id") String id,Model model){
        Car car = carService.getById(id);
        model.addAttribute("entity",car);
        return "car/carView";
    }

    /**
     * 05-跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("car_toEdit/{id}")
    public String toEdit(@PathVariable("id") String id,Model model){
        Car car = carService.getById(id);
        model.addAttribute("entity",car); //回显数据的对象
        return "car/carEdit";
    }


    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("car_update")
    public CommonResult update(Car car, HttpSession session) {

        try {
            //对象是数据库中原对象
            Car old_car = carService.getById(car.getId());

            if(!car.getImgUrl().equals(old_car.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= old_car.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                File f=new File(realPath);
                if(f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }

            //设置当前系统时间为更新事件
            car.setModifyTime(DateUtils.getNowTime());
            //设置新增路线的人(暂时写死，后期从session中获取)
            car.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+car);
            return new CommonResult(200,"请求成功",carService.updateById(car));
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
    @GetMapping("car_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        Car car = carService.getById(id);
        car.setDeleteStatus(1L);//删除状态
        carService.updateById(car);
        return new CommonResult(200,"请求成功");
    }
}
