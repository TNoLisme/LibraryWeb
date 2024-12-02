package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.Entity.Admin;
import com.example.QuanLyThuVien.Repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;

    }

    // Kiểm tra đăng nhập
    public Admin login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email); // Tìm admin theo email
        if (admin != null && admin.getPassword().equals(password)) { // So sánh mật khẩu
            return admin; // Đăng nhập thành công
        } else {
            return null; // Đăng nhập thất bại
        }
    }
}
