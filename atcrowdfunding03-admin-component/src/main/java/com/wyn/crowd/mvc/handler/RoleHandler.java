package com.wyn.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.wyn.crowd.mapper.entity.Role;
import com.wyn.crowd.service.api.RoleService;
import com.wyn.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/17
 */
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.json")// 掌握json数据格式的注解@RequestBody
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleList){
        roleService.removeRole(roleList);
        return ResultEntity.successWithData();
    }

    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        System.out.println(role);
        return ResultEntity.successWithData();
    }

    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String > saveRole(Role role){
        roleService.addRole(role);
         return ResultEntity.successWithData();
    }

    @PreAuthorize("hasRole('部长')")
//    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }
}
