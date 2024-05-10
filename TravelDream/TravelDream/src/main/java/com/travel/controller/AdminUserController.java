package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.AdminUser;
import com.travel.pojo.User;
import com.travel.service.AdminUserService;
import com.travel.utils.CommonResult;
import com.travel.utils.DateUtils;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-17 15:36
 * @Description: TODO
 */
@Controller
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    /**
     * 01-管理员登录
     * @param adminUser
     * @return
     */
    @PostMapping("adminUser_login")
    public String login(AdminUser adminUser, String userCode,HttpSession session, Model model){
        String sysCode = (String) session.getAttribute("sysCode"); //系统正确的验证码
        if(sysCode.equalsIgnoreCase(userCode)){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("USER_NAME",adminUser.getUserName());
            queryWrapper.eq("PASSWORD",adminUser.getPassword());
            queryWrapper.eq("DELETE_STATUS","0");
            AdminUser loginAdminUser = adminUserService.getOne(queryWrapper);
            if(loginAdminUser!=null){
                if(loginAdminUser.getState()==1){
                    System.out.println("登录成功!"+loginAdminUser);
                    session.setAttribute("admin",loginAdminUser);
                    return "index";
                }else{
                    model.addAttribute("message","该账户被禁用！");
                    return "login";
                }
            }else{
                System.out.println("登录失败");
                model.addAttribute("message","账号或密码错误！");
                return "login";
            }
        }else{
            model.addAttribute("message","验证码错误！");
            return "login";
        }
    }

    /**
     * 02-管理员退出
     * @param session
     * @return
     */
    @GetMapping("adminUser_logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }


    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("admin_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String userName, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(userName)){
            queryWrapper.like("USER_NAME",userName);
        }
        IPage page = adminUserService.page(new Page<AdminUser>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<AdminUser> pagerHelper=new PagerHelper<AdminUser>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("userName",userName); //查询数据回显
        return "admin/adminList";
    }

    /**
     * 02-打开一个新增旅游路线页面
     * @return
     */
    @GetMapping("admin_toadd")
    public String toadd(){
        return "admin/adminAdd";
    }

    /**
     * 03-新增(异步新增)
     * @return
     */
    @ResponseBody
    @PostMapping("admin_add")
    public CommonResult add(AdminUser adminUser, HttpSession session){
        //设置当前系统时间
        adminUser.setAddTime(DateUtils.getNowTime());
        //设置新增路线的人(暂时写死，后期从session中获取)
        adminUser.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+adminUser);
        return new CommonResult(200,"请求成功",adminUserService.save(adminUser));
    }

    /**
     * 04-跳转详情页面
     * @return
     */
    @GetMapping("admin_todetail/{id}")
    public String todetail(@PathVariable("id") String id,Model model){
        AdminUser adminUser = adminUserService.getById(id);
        model.addAttribute("entity",adminUser);
        return "admin/adminView";
    }

    /**
     * 05-跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("admin_toEdit/{id}")
    public String toEdit(@PathVariable("id") String id,Model model){
        AdminUser adminUser = adminUserService.getById(id);
        model.addAttribute("entity",adminUser); //回显数据的对象
        return "admin/adminEdit";
    }


    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("admin_update")
    public CommonResult update(AdminUser adminUser, HttpSession session) {
        //设置当前系统时间为更新事件
        adminUser.setModifyTime(DateUtils.getNowTime());
        //设置新增路线的人(暂时写死，后期从session中获取)
        adminUser.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要更新的对象是:"+adminUser);
        return new CommonResult(200,"请求成功",adminUserService.updateById(adminUser));
    }

    /**
     * 07-删除用户
     * @param id
     * @return
     */
    @GetMapping("admin_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        AdminUser adminUser = adminUserService.getById(id);
        adminUser.setDeleteStatus(1);//删除状态
        adminUserService.updateById(adminUser);
        return new CommonResult(200,"请求成功");
    }








}
