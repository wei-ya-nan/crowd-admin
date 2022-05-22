package com.wyn.crowd.service.api;

import com.github.pagehelper.PageInfo;

import com.wyn.crowd.mapper.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/15
 */

public interface AdminService {
    void saveAdmin(Admin admin);
     List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void udpdate(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String loginAcct);
}
