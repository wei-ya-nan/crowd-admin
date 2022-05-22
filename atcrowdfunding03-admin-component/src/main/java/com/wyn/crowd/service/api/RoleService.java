package com.wyn.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.wyn.crowd.mapper.entity.Role;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/17
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void addRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
