package com.travel.controller;

import com.travel.utils.CommonResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.controller
 * @CreateTime: 2021-05-18 14:52
 * @Description: TODO
 */
@Controller
public class FileUploadController {


    /**
     * 通用的文件上传
     * @param multipartFile
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadImg/{address}")
    @ResponseBody
    public CommonResult upload(@PathVariable("address") String address,@RequestParam("file") MultipartFile multipartFile, HttpSession session) throws Exception{
        if (!StringUtils.isEmpty(multipartFile) && multipartFile.getSize()>0){
            //获取原始的文件名
            String filename = multipartFile.getOriginalFilename();
            //获取文件的扩展名
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            //文件上传的真实路径
            String realPath = ResourceUtils.getURL("classpath:").getPath();
            String filePath="/"+address+"/"+new Date().getTime()+"."+suffix;
            realPath=realPath.substring(1,realPath.length())+"static"+filePath;
            File newfile = new File(realPath);
            try {
                multipartFile.transferTo(newfile);
                System.out.println("文件上传路径是:"+realPath);
                return new CommonResult(200,"文件上传成功",filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return new CommonResult(500,"文件上传失败");
            }
        }else {
            return new CommonResult(250,"上传文件为空");
        }
    }

}
