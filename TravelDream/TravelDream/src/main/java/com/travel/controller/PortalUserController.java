package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.travel.pojo.AdminUser;
import com.travel.pojo.User;
import com.travel.service.UserMessageService;
import com.travel.service.UserService;
import com.travel.utils.CommonResult;
import com.travel.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-18 16:18
 * @Description: TODO
 */
@Controller
public class PortalUserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMessageService userMessageService;

    /**
     * 01-跳转前台登录页面
     *
     * @return
     */
    @GetMapping("user_tologin")
    public String tologin() {
        return "portal/login";
    }


    /**
     * 02-会员登录
     *
     * @param user
     * @return
     */
    @PostMapping("user_login")
    public String login(User user, String userCode, HttpSession session, Model model) {
        String sysCode = (String) session.getAttribute("sysCode"); //系统正确的验证码
        if (sysCode.equalsIgnoreCase(userCode)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("USER_NAME", user.getUserName());
            queryWrapper.eq("PASSWORD", user.getPassword());
            queryWrapper.eq("DELETE_STATUS", "0");
            User loginUser = userService.getOne(queryWrapper);
            if (loginUser != null) {
                if (loginUser.getState() == 1) {
                    System.out.println("登录成功!" + loginUser);
                    session.setAttribute("user", loginUser);
                    session.setAttribute("userName", loginUser.getUserName());
                    //跳转个人详情页面取值
                    model.addAttribute("entity", loginUser);
                    return "portal/userCenter";
                } else {
                    model.addAttribute("message", "该账户被禁用！");
                    return "portal/login";
                }
            } else {
                System.out.println("登录失败");
                model.addAttribute("message", "账号或密码错误！");
                return "portal/login";
            }
        } else {
            model.addAttribute("message", "验证码错误！");
            return "portal/login";
        }
    }

    /**
     * 跳转注册页面
     *
     * @return
     */
    @GetMapping("/user_toregister")
    public String toregister() {
        return "portal/register";
    }

    /**
     * 会员注册
     *
     * @param user
     * @return
     */
    @PostMapping("user_register")
    public void register(User user,HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try {
            user.setDeleteStatus(0);
            user.setState(1L); //启用状态
            user.setAddTime(DateUtils.getNowTime());
            boolean b = userService.save(user);
            //注册成功
            response.getWriter().write("<script>alert('会员注册成功,现在去登录！');location.href='/user_tologin';</script>");
        } catch (Exception e) {
            try {
                response.getWriter().write("<script>alert('会员注册失败,稍后再试！');location.href='/user_toregister';</script>");
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
            }
        }
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @GetMapping("user_logout")
    public void logout(HttpSession session,HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        //session失效
        session.invalidate();
        response.getWriter().print("<script>alert('注销成功');location.href='/user_tologin';</script>");
    }

    /**
     * 跳转到更新密码页面
     * @return
     */
    @GetMapping("user_tochangePassword")
    public String tochangePassword(){
        return "portal/changePassword";
    }

    /**
     * 会员修改密码
     * @param password
     * @param newPassword
     * @param checkPassword
     * @param session
     * @return
     */
    @PostMapping("/user_changePassword")
    @ResponseBody
    public CommonResult changePassword(String password,String newPassword,String checkPassword,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(StringUtils.isEmpty(newPassword)||StringUtils.isEmpty(checkPassword)){
            return new CommonResult(500,"要修改的密码不能为空!");
        }else if(!newPassword.equals(checkPassword)){
            return new CommonResult(500,"两次输入的密码不一致!");
        }else if(!user.getPassword().equals(password)){
            return new CommonResult(500,"原密码输入错误!");
        }else if(password.equals(newPassword)){
            return new CommonResult(500,"原密码不能和新密码相同!");
        }else{
            user.setPassword(newPassword);
            boolean b = userService.updateById(user); //更新数据库
            if(b){
                session.invalidate();
                return new CommonResult(200,"密码修改成功!");
            }else{
                return new CommonResult(500,"密码更新异常");
            }
        }
    }

    /**
     * 跳转个人资料
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/user_topersonInfo")
    public String topersonInfo(HttpSession session, Model model){
        User user= (User) session.getAttribute("user");
        model.addAttribute("entity",user);
        return "portal/personalIntro";
    }

    /**
     * 跳转用户中心
     * @return
     */
    @GetMapping("user_tocenter")
    public String tocenter(HttpSession session, Model model){
        User user= (User) session.getAttribute("user");
        model.addAttribute("entity",user);
        return "portal/userCenter";
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("user_updateInfo")
    public String user_updateInfo(User user,Model model){
        try {
            user.setState(1L);
            boolean b = userService.updateById(user);
            model.addAttribute("message","个人信息更新成功");
        } catch (Exception e) {
            model.addAttribute("message","个人信息更新异常");
        }
        User u = userService.getById(user.getId());
        model.addAttribute("entity",u);
        return "portal/personalIntro";
    }

    /**
     * 跳转留言界面
     * @return
     */
    @GetMapping("user_tomyMessage")
    public String tomyMessage(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        //查询当前人的留言条数
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.eq("USER_ID",user.getId());
        int count = userMessageService.count(queryWrapper);

        model.addAttribute("messageCount",count);
        return "portal/myMessage";
    }
}
