package com.wyn.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.wyn.crowd.constant.CrowdConstant;
import com.wyn.crowd.mapper.entity.Admin;
import com.wyn.crowd.service.api.AdminService;
import com.wyn.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/16
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/update.html")
    public String updateAdmin(Admin admin, @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("keyword") String keyword){
        adminService.udpdate(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;

    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword") String keyword,ModelMap modelMap) {
       Admin admin =  adminService.getAdminById(adminId);
       modelMap.addAttribute("admin", admin);
       return "admin-edit";
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {
        //执行删除
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/get/page.html")
    // 使用默认值来指定空值是的情况
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                              ModelMap modelMap
    ) {

        // 调用service方法
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd
            , HttpSession session) {

        //service方法进行登录检查
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
//        return "admin-main";
        return "redirect:/admin/to/main/page.html";
    }

//    @PreFilter(value = "")
//    @ResponseBody
//    @RequestMapping("/admin/test/pre/filter")
//    public ResultEntity<String> saveList(@RequestBody List<Integer> valueList){
//
//
//        return null;
//    }
}
