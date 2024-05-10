package com.travel.interceptor;

import com.travel.pojo.AdminUser;
import com.travel.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.interceptor
 * @CreateTime: 2021-05-25 14:24
 * @Description: 简单的登录拦截处理
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户访问的地址
        String requestURI = request.getRequestURI();
        //requestURI=/user_myorderlist
        System.out.println("requestURI="+requestURI);
        if (requestURI.startsWith("/user_")){
            //前台用户访问地址
            HttpSession session = request.getSession();
            User user= (User) session.getAttribute("user");
            if(user!=null){
                return true;
            }else{
                //前台登录入口
                response.getWriter().print("<script>location.href='/user_tologin';</script>");
                return false;
            }
        }else{
            HttpSession session = request.getSession();
            AdminUser admin = (AdminUser) session.getAttribute("admin");
            //管理员登录
            if(admin!=null){
                return true;
            }else{
                //管理员跳转登录
                response.getWriter().print("<script>location.href='/admin';</script>");
                return false;
            }
        }
    }
}
