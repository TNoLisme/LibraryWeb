package com.example.QuanLyThuVien.Controller;

import com.example.QuanLyThuVien.Entity.Admin;
import com.example.QuanLyThuVien.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Admin admin = adminService.login(email, password);
        if (admin != null) {
            return ResponseEntity.ok(admin); // Đăng nhập thành công, trả về thông tin Admin
        } else {
            return ResponseEntity.status(401).body("Invalid email or password"); // Đăng nhập thất bại
        }
    }
}

