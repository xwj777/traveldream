package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.TravelRoute;
import com.travel.pojo.UserOrder;
import com.travel.service.UserOrderService;
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
 * @CreateTime: 2021-05-22 09:26
 * @Description: TODO
 */
@Controller
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("userOrder_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String userName, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(userName)){
            queryWrapper.like("USER_Name",userName);
        }
        IPage page = userOrderService.page(new Page<UserOrder>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<UserOrder> pagerHelper=new PagerHelper<UserOrder>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("userName",userName); //查询数据回显
        return "order/orderList";
    }

    /**
     * 02-订单详情数据展示
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("userOrder_view/{id}")
    public String view(@PathVariable("id") String id,Model model){
        model.addAttribute("entity",userOrderService.getById(id));
        return "order/orderView";
    }

    /**
     * 03-跳转更新页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("userOrder_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id,Model model){
        model.addAttribute("entity",userOrderService.getById(id));
        return "order/orderEdit";
    }

    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("userOrder_update")
    public CommonResult update(UserOrder userOrder, HttpSession session) {
        try {
            //设置当前系统时间为更新事件
            userOrder.setModifyTime(DateUtils.getNowTime());
            //设置新增路线的人(暂时写死，后期从session中获取)
            //TODO :后期从session获取
            userOrder.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+userOrder);
            return new CommonResult(200,"请求成功",userOrderService.updateById(userOrder));
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
    @GetMapping("userOrder_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        UserOrder userOrder = userOrderService.getById(id);
        userOrder.setDeleteStatus(1L);//删除状态
        userOrderService.updateById(userOrder);
        return new CommonResult(200,"请求成功");
    }
}
