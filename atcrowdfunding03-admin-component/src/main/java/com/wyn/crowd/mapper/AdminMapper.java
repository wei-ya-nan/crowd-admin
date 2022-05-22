package com.wyn.crowd.mapper;



import com.wyn.crowd.mapper.entity.Admin;
import com.wyn.crowd.mapper.entity.AdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    List<Admin> selectAdminByKeyword(@Param("keyword") String keyword);

    void deleteRelationship(@Param("adminId") Integer adminId);

    void insertNewRelationShip(@Param("adminId") Integer adminId, @Param("roleList") List<Integer> roleList);
}