package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.pojo.Insurance;
import com.travel.pojo.TravelRoute;
import com.travel.service.InsuranceService;
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
 * @CreateTime: 2021-05-24 09:24
 * @Description: TODO
 */
@Controller
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    /**
     * 01-分页查询列表
     * @return
     */
    @RequestMapping("insurance_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "7") Long pageSize,
                       @RequestParam(defaultValue = "") String title, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("DELETE_STATUS",0);
        queryWrapper.orderByDesc("ADD_TIME");
        if(!"".equals(title)){
            queryWrapper.like("TITLE",title);
        }
        IPage page = insuranceService.page(new Page<Insurance>(pageNumber, pageSize), queryWrapper);
        //封装工具类
        PagerHelper<Insurance> pagerHelper=new PagerHelper<Insurance>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title",title); //查询数据回显
        return "insurance/insuranceList";
    }

    /**
     * 02-打开一个新增旅游路线页面
     * @return
     */
    @GetMapping("insurance_toadd")
    public String toadd(){
        return "insurance/insuranceAdd";
    }

    /**
     * 03-新增(异步新增)
     * @return
     */
    @ResponseBody
    @PostMapping("insurance_add")
    public CommonResult add(Insurance insurance, HttpSession session){
        //设置当前系统时间
        insurance.setAddTime(DateUtils.getNowTime());
        //TODO:设置新增路线的人(暂时写死，后期从session中获取)
        insurance.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+insurance);
        return new CommonResult(200,"请求成功",insuranceService.save(insurance));
    }

    /**
     * 04-跳转详情页面
     * @return
     */
    @GetMapping("insurance_todetail/{id}")
    public String todetail(@PathVariable("id") String id,Model model){
        Insurance insurance = insuranceService.getById(id);
        model.addAttribute("entity",insurance);
        return "insurance/insuranceView";
    }

    /**
     * 05-跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("insurance_toEdit/{id}")
    public String toEdit(@PathVariable("id") String id,Model model){
        Insurance insurance = insuranceService.getById(id);
        model.addAttribute("entity",insurance);
        return "insurance/insuranceEdit";
    }


    /**
     * 06-更新(异步更新)
     * @return
     */
    @ResponseBody
    @PostMapping("insurance_update")
    public CommonResult update(Insurance insurance, HttpSession session) {

        try {
            //对象是数据库中原对象
            Insurance old_insurance = insuranceService.getById(insurance.getId());

            if(!insurance.getImgUrl().equals(old_insurance.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= old_insurance.getImgUrl();  // /travelRoute/广州_上海.jpg
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                File f=new File(realPath);
                if(f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }

            //设置当前系统时间为更新事件
            insurance.setModifyTime(DateUtils.getNowTime());
            //TODO:设置新增路线的人(暂时写死，后期从session中获取)
            insurance.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+insurance);
            return new CommonResult(200,"请求成功",insuranceService.updateById(insurance));
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
    @GetMapping("insurance_delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable("id") String id){
        //TODO:后期需要补代码判断这个旅游路线是否在使用用，如果在使用中提示暂时不能删除
        Insurance insurance = insuranceService.getById(id);
        insurance.setDeleteStatus(1L);//删除状态
        insuranceService.updateById(insurance);
        return new CommonResult(200,"请求成功");
    }

}
