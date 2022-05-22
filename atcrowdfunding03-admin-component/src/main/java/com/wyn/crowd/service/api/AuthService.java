package com.wyn.crowd.service.api;

import com.wyn.crowd.mapper.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/21
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
