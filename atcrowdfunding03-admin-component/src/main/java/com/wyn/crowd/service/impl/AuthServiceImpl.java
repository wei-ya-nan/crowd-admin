package com.wyn.crowd.service.impl;

import com.wyn.crowd.mapper.AuthMapper;
import com.wyn.crowd.mapper.entity.Auth;
import com.wyn.crowd.mapper.entity.AuthExample;
import com.wyn.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/21
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 获取map中roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 删除旧的关联的数据
        authMapper.deleteOldRelationShip(roleId);

        // 获取roleIdList的值
        List<Integer> authIdList = map.get("authIdArray");

        // 判断authIdList是否有效
        if(authIdList != null && authIdList.size() > 0){
            authMapper.insertNewRelationShip(roleId,authIdList);

        }
    }

    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
