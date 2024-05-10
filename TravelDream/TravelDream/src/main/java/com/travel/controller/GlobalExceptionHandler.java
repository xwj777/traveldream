package com.travel.controller;

import com.travel.utils.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: ssm2021
 * @BelongsPackage: com.ssm.web
 * @CreateTime: 2021-05-14 09:52
 * @Description: 整个项目的全局异常统一处理
 * @controlleradvice + @ ExceptionHandler 也可以实现全局的异常捕捉
 */
//@RestController
//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public CommonResult handleArithmeticException(Exception ex) {
        CommonResult result = new CommonResult(500, "请求异常");
        return result;
    }
}
