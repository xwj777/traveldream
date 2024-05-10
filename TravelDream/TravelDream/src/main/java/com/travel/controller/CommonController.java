package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.travel.pojo.AdminUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-17 15:41
 * @Description: 通用的控制器
 */
@Controller
public class CommonController {

    /**
     * 01-跳转门户前台首页
     * @return
     */
    @GetMapping("/")
    public String toProtalIndex(Model model){
        model.addAttribute("val","index");
        return "portal/index";
    }

    /**
     * 02-跳转管理员登录页面
     * @return
     */
    @GetMapping("admin")
    public String toLogin(){
        return "login";
    }
}
