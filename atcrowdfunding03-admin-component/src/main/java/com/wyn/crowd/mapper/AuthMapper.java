package com.wyn.crowd.mapper;


import java.util.List;

import com.wyn.crowd.mapper.entity.Auth;
import com.wyn.crowd.mapper.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

    void deleteOldRelationShip(@Param("roleId") Integer roleId);

    void insertNewRelationShip(@Param("roleId") Integer roleId, @Param("authIdList") List<Integer> authIdList);

    List<String> selectAssignedAuthNameByAdminId(@Param("adminId") Integer adminId);
}