package com.wyn.crowd.mapper;


import java.util.List;

import com.wyn.crowd.mapper.entity.Role;
import com.wyn.crowd.mapper.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectRoleByKeyword(String keyword);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAssignedRole(@Param("adminId") Integer adminId);

    List<Role> selectUnAssignedRole(@Param("adminId") Integer adminId);
}