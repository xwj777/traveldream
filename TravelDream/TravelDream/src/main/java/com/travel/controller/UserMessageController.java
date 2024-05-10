package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.TravelRoute;
import com.travel.pojo.UserMessage;
import com.travel.service.UserMessageService;
import com.travel.utils.CommonResult;
import com.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-21 10:40
 * @Description: TODO
 */
@Controller
public class UserMessageController {

    @Autowired
    UserMessageService userMessageService;


    /**
     * 01-留言分页
     * @return
     */
    @RequestMapping("message_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String userName, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(userName)){
            queryWrapper.like("USER_NAME",userName);
        }
        IPage page = userMessageService.page(new Page<UserMessage>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        //封装工具类
        PagerHelper<UserMessage> pagerHelper=new PagerHelper<UserMessage>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("userName",userName); //查询数据回显
        return "message/messageList";
    }

    /**
     * 02-查询用户留言详情
     * @param id
     * @return
     */
    @RequestMapping("message_View/{id}")
    public String message_View(@PathVariable("id") String id,Model model){
        UserMessage userMessage = userMessageService.getById(id);
        userMessage.setState(1);//留言已读
        userMessageService.updateById(userMessage);
        model.addAttribute("entity",userMessage);
        return "message/messageView";
    }

    /**
     * 03-跳转回复留言界面
     * @param id
     * @return
     */
    @RequestMapping("message_replyView/{id}")
    public String message_replyView(@PathVariable("id") String id,Model model){
        UserMessage userMessage = userMessageService.getById(id);
        userMessage.setState(1);//留言已读
        userMessageService.updateById(userMessage);
        model.addAttribute("entity",userMessage);
        return "message/messageReply";
    }

    /**
     * 留言回复
     * @param id
     * @param replyContent
     * @return
     */
    @ResponseBody
    @RequestMapping("replyMessage")
    public CommonResult replyMessage(String id,String replyContent){
        UserMessage userMessage = userMessageService.getById(id);
        userMessage.setReplyContent(replyContent);
        userMessageService.updateById(userMessage);
        return new CommonResult(200,"请求成功");
    }

    /**
     * 留言删除
     * @param id
     * @return
     */
    @RequestMapping("messageDelete/{id}")
    @ResponseBody
    public CommonResult messageDelete(@PathVariable("id") String id){
        UserMessage userMessage = userMessageService.getById(id);
        userMessage.setDeleteStatus(1);
        userMessageService.updateById(userMessage);
        return new CommonResult(200,"请求成功");
    }
}
