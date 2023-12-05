package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.AdminEntity;
import com.csit321.bloodcount.Entity.DonorEntity;
import com.csit321.bloodcount.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/insertAdmin")
    public AdminEntity insertAdmin(@RequestBody AdminEntity admin) {
        return adminService.insertAdmin(admin);
    }

    @GetMapping("/getAllAdmins")
    public List<AdminEntity> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PutMapping("/updateAdmin")
    public AdminEntity updateAdmin(@RequestParam int adminId, @RequestBody AdminEntity newAdminDetails) {
        return adminService.updateAdmin(adminId, newAdminDetails);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")
    public String deleteAdmin(@PathVariable int adminId) {
        return adminService.deleteAdmin(adminId);
    }
}
