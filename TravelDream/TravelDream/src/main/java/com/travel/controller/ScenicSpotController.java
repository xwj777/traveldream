package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.ScenicSpot;
import com.travel.pojo.User;
import com.travel.pojo.UserMessage;
import com.travel.service.ScenicSpotService;
import com.travel.service.UserMessageService;
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
 * @CreateTime: 2021-05-19 10:34
 * @Description: TODO
 */
@Controller
public class ScenicSpotController {

    @Autowired
    ScenicSpotService scenicSpotService;

    /**
     * 01-分页查询列表
     *
     * @return
     */
    @RequestMapping("scenicSpot_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String name, Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS", 0);
        queryWrapper.orderByDesc("ADD_TIME");
        if (!"".equals(name)) {
            queryWrapper.like("SPOT_NAME", name);
        }
        IPage page = scenicSpotService.page(new Page<ScenicSpot>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper<ScenicSpot>(pageNumber, pageSize, page.getPages(), page.getTotal(), page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("name", name); //查询数据回显
        return "scenicSpot/scenicSpotList";
    }

    /**
     * 02-跳转到景点新增页面
     *
     * @return
     */
    @GetMapping("scenicSpot_toAdd")
    public String toAdd() {
        return "scenicSpot/scenicSpotAdd";
    }

    /**
     * 03-新增景点
     *
     * @return
     */
    @PostMapping("scenicSpot_Add")
    @ResponseBody
    public CommonResult add(ScenicSpot scenicSpot) {
        scenicSpot.setAddTime(DateUtils.getNowTime());
        //TODO:后期项目完成之后，改成Session中获取当前登录用户ID
        scenicSpot.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        scenicSpot.setDeleteStatus(0);
        return new CommonResult(200, "请求成功", scenicSpotService.save(scenicSpot));
    }

    /**
     * 04-跳转详情页面
     *
     * @param id
     * @return
     */
    @GetMapping("scenicSpot_View/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        model.addAttribute("entity", scenicSpotService.getById(id));
        return "scenicSpot/scenicSpotView";
    }

    /**
     * 05-跳转更新页面
     *
     * @param id
     * @return
     */
    @GetMapping("scenicSpot_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id, Model model) {
        model.addAttribute("entity", scenicSpotService.getById(id));
        return "scenicSpot/scenicSpotEdit";
    }

    /**
     * 06-更新(异步更新)
     *
     * @return
     */
    @ResponseBody
    @PostMapping("scenicSpot_update")
    public CommonResult update(ScenicSpot scenicSpot, HttpSession session) {
        try {
            //对象是数据库中原对象
            ScenicSpot spot = scenicSpotService.getById(scenicSpot.getId());

            if (!scenicSpot.getImgUrl().equals(spot.getImgUrl())) {
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath = spot.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath = realPath.substring(1, realPath.length()) + "static" + filePath;
                File f = new File(realPath);
                if (f.exists()) {
                    f.delete();
                    System.out.println("删除了老的图片,地址是：" + realPath);
                }
            }
            //设置当前系统时间为更新事件
            scenicSpot.setModifyTime(DateUtils.getNowTime());
            //设置新增路线的人(暂时写死，后期从session中获取)
            scenicSpot.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:" + scenicSpot);
            return new CommonResult(200, "请求成功", scenicSpotService.updateById(scenicSpot));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(500, "请求失败");
        }
    }

    /**
     * 07-删除景点
     *
     * @param id
     * @return
     */
    @GetMapping("scenicSpot_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id) {
        //TODO:后期需要补代码判断这个景点是否在使用用，如果在使用中提示暂时不能删除
        ScenicSpot scenicSpot = scenicSpotService.getById(id);
        scenicSpot.setDeleteStatus(1);//删除状态
        scenicSpotService.updateById(scenicSpot);
        return new CommonResult(200, "请求成功");
    }
}
