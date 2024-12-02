package com.example.QuanLyThuVien.Controller;


import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/members")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = membersService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return membersService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        Optional<Member> member = membersService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member memberDetails) {
        Member updatedMember = membersService.updateMember(id, memberDetails);
        return updatedMember != null ? ResponseEntity.ok(updatedMember) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        membersService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        boolean isLoginValid = membersService.checkLogin(email, password);
        if (isLoginValid) {
            return ResponseEntity.status(HttpStatus.OK).body("Đăng nhập thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email hoặc mật khẩu không đúng");
        }
    }
}