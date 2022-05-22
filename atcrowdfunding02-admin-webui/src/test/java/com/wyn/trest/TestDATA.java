package com.wyn.trest;


import com.sun.istack.internal.NotNull;
import com.wyn.crowd.mapper.AdminMapper;
import com.wyn.crowd.mapper.RoleMapper;
import com.wyn.crowd.mapper.entity.Admin;
import com.wyn.crowd.mapper.entity.Role;
import com.wyn.crowd.service.api.AdminService;
import com.wyn.crowd.service.api.RoleService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;


/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/14
 */
//spring整合junit的测试注解
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})

public class TestDATA {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test09(){
        Admin admin  = new Admin(null,"tom","123", "汤姆", "tom123@qq.com", null);
        adminService.saveAdmin(admin);
    }


    @Test
    public void test() {
        for (int i = 0; i < 238; i++) {
            UUID uuid = UUID.randomUUID();
            String s = uuid.toString();
            adminMapper.insert(new Admin(null, "loginAcct" + i, "userPswd" + i, s.substring(0, 7), "email" + i,
                    null));
        }
    }

    @Test
    public void testServiceTx() {
        Admin admin = new Admin(null, "mike", "123", "ta", "123@gmail.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testjdbc() throws SQLException {
        Connection connection = dataSource.getConnection();
        if (connection != null) {
            System.out.println("good");
        }
    }

    @Test
    public void testMapper() {
        Admin admin = new Admin(null, "tom", "123", "ta", "123@gmail.com", null);
        int i = adminMapper.insertSelective(admin);
        System.out.println("jjdkla=" + i);
    }

    @Test
    @Transactional
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(TestDATA.class);
        logger.info("hello info");

    }

    @Test
    public void testInsertRole(){
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null, "role"+i));
        }
    }

    public void testDemo(@NotNull Object...args){
        for (Object arg : args) {

        }
    }

}
