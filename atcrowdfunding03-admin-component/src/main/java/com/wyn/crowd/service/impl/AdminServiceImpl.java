package com.wyn.crowd.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyn.crowd.constant.CrowdConstant;
import com.wyn.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.wyn.crowd.exception.LoginAcctAlreadyInUserException;
import com.wyn.crowd.exception.LoginFailedException;
import com.wyn.crowd.mapper.AdminMapper;
import com.wyn.crowd.mapper.entity.Admin;
import com.wyn.crowd.mapper.entity.AdminExample;
import com.wyn.crowd.service.api.AdminService;
import com.wyn.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/15
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveAdmin(Admin admin) {
        // 密码加密
        String userPswd = admin.getUserPswd();
//        userPswd = CrowdUtil.md5(userPswd);
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creatTime = dateFormat.format(date);
        admin.setCreateTime(creatTime);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUserException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 1 、根据登录账号查询admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size() == 0 || admins == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 2 、判断admin对象是否为空 如果admin对象为空则抛出异常
        Admin admin = admins.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 3 、如果admin对象不为空，则将数据库中 的admin的密码取出来
        String pswd = admin.getUserPswd();
        // 4 、将表单提交明文密码进行加密
        String md5Pswd = CrowdUtil.md5(userPswd);
        // 5 、 对密码进行比较 比较不一致则抛出异常，比较一致则返回admin对象
        if (!pswd.equals(md5Pswd)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }

    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //调用pageHelper的静态方法开启分页功能 体现了非侵入的设计
        PageHelper.startPage(pageNum, pageSize);

        //执行查询
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        return new PageInfo<Admin>(admins);
    }

    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    public void udpdate(Admin admin) {
        //Selective表示有选择行的更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 根据adminId删除旧的关联关系数据
        adminMapper.deleteRelationship(adminId);

        // 根据roleList和adminId爆粗新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationShip(adminId,roleIdList);

        }

    }

    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin = admins.get(0);

        return admin;
    }
}
