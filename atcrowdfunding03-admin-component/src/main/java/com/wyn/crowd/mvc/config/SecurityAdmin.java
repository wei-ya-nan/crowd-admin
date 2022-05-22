package com.wyn.crowd.mvc.config;

import com.sun.istack.internal.NotNull;
import com.wyn.crowd.mapper.entity.Admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/27
 * 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门为创建这个类对User类进行扩展
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID  = 1L;

    // 原始的admin对象，包含了Admin对象的全部属性
    private Admin originalAdmin;
                                //  传入原始的admin对象  // 创建角色的，权限信息的集合
    public SecurityAdmin(@NotNull Admin originalAdmin, List<GrantedAuthority> authorities){
        // 调用父类的构造器
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);

        this.originalAdmin = originalAdmin;

        // 将原始的admin对象中的密码擦除 增加安全性
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
