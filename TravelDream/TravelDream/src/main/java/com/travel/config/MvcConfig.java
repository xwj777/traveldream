package com.travel.config;

import com.travel.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.config
 * @CreateTime: 2021-05-25 14:26
 * @Description: 配置Spring Mvc拦截器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");

        //添加不拦截路径
        registration.excludePathPatterns(
                "/",                        //前台首页不拦截
                "/createImage",            //验证码
                "/adminUser_login",       //后台用户登录
                "/admin",                //跳转后台登录地址
                "/css/**",
                "/hotel/**",
                "/insurance/**",
                "/js/**",
                "/layui/**",
                "/message/**",
                "/scenicSpot/**",
                "/strategy/**",
                "/travelRoute/**",
                "/images/**",
                "/car/**",
                "/favicon.ioc",
                "/portal_*",  //前台某些地址不拦截
                "/portal_*/**",  //前台某些地址不拦截
                "/user_tologin",  //前台前台跳转登录
                "/user_login",  //前台登录
                "/user_toregister",  //前台前台跳转注册
                "/user_register"  //前台注册
        );
    }
}
