package com.travel.controller;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-20 16:14
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.TravelRoute;
import com.travel.pojo.User;
import com.travel.pojo.UserMessage;
import com.travel.service.UserMessageService;
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
import java.util.List;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.service.impl
 * @CreateTime: 2021-05-20 15:45
 * @Description: TODO
 */
@Controller
public class PortalUserMessageController {

    @Autowired
    UserMessageService userMessageService;

    /**
     * 后台会员留言的接口
     * @param message
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("user_leaveMessage")
    public CommonResult leaveMessage(UserMessage message, HttpSession session){
        User user= (User) session.getAttribute("user");
        message.setAddTime(DateUtils.getNowTime());
        message.setAddUserId(user.getId());
        message.setDeleteStatus(0);
        message.setState(0);
        message.setUserId(user.getId());
        message.setUserName(user.getUserName());
        message.setName(user.getName());
        return new CommonResult(200,"请求成功",userMessageService.save(message));
    }

    /**
     * 查询我的留言信息 分页
     * @return
     */
    @RequestMapping("/user_messageList")
    public String messageList(@RequestParam(defaultValue = "1") Long pageNumber,
                              @RequestParam(defaultValue = "7") Long pageSize, HttpSession session, Model model){
        //当前用户信息
        User user= (User) session.getAttribute("user");

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        queryWrapper.eq("USER_ID",user.getId());
        IPage page = userMessageService.page(new Page<UserMessage>(pageNumber, pageSize), queryWrapper);

        //封装工具类
        PagerHelper<UserMessage> pagerHelper=new PagerHelper<UserMessage>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/messageList";
    }

    @RequestMapping("user_messageView/{id}")
    public String messageView(@PathVariable("id") String id,Model model){
        UserMessage userMessage = userMessageService.getById(id);
        model.addAttribute("userMessage",userMessage);
        return "portal/messageView";
    }
}
