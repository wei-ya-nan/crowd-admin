package com.wyn.crowd.mvc.handler;

import com.sun.istack.internal.NotNull;
import com.wyn.crowd.mapper.entity.Auth;
import com.wyn.crowd.mapper.entity.Role;
import com.wyn.crowd.service.api.AdminService;
import com.wyn.crowd.service.api.AuthService;
import com.wyn.crowd.service.api.RoleService;
import com.wyn.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/21
 */
@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithData();

    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId")Integer roleId){

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);

    }

    @ResponseBody
    @RequestMapping("assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){

        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }


    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") String pageNum,
                                            @RequestParam("keyword") String keyword,
                                            // ?????????????????? ???????????????????????????????????????????????????required = false?????????????????????????????????
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        System.out.println(roleIdList.size()+"?????????????????????????????????");;

        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/assign/to/role/page")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, @NotNull ModelMap modelMap) {

        // ????????????????????????
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // ????????????????????????
        List<Role> unAssignRoleList = roleService.getUnAssignedRole(adminId);

        // ????????????unAssignedRoleList  assignedRoleList
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignRoleList);
        return "assign-role";
    }

}
