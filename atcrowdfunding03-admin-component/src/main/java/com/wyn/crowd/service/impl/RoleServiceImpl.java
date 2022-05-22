package com.wyn.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyn.crowd.mapper.RoleMapper;
import com.wyn.crowd.mapper.entity.Role;
import com.wyn.crowd.mapper.entity.RoleExample;
import com.wyn.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/17
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 开启分页的功能
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<Role> list = roleMapper.selectRoleByKeyword(keyword);

        // 封装PageInfo返回
        return new PageInfo<Role>(list);
    }

    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    public void removeRole(List<Integer> roleList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleList);
        roleMapper.deleteByExample(roleExample);

    }

    public List<Role> getAssignedRole(Integer adminId) {
        return  roleMapper.selectAssignedRole(adminId);

    }

    public List<Role> getUnAssignedRole(Integer adminId) {
        return  roleMapper.selectUnAssignedRole(adminId);
    }
}
