package com.wyn.crowd.mvc.interceptor;

import com.wyn.crowd.constant.CrowdConstant;

import com.wyn.crowd.exception.AccessForbiddenException;
import com.wyn.crowd.mapper.entity.Admin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/16
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  通过session对象获取Session对象
        Admin admin = (Admin) (request.getSession().getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN));
        if(admin == null){
            //抛出异常
            throw  new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        // 放行
        return true;
    }

}
