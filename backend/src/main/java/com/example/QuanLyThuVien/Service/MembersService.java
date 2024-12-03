package com.example.QuanLyThuVien.Service;


import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembersService {

    @Autowired
    private MemberRepository membersRepository;

    // Create or update a member
    public Member saveMember(Member member) {
        return membersRepository.save(member);
    }

    // Get all members
    public List<Member> getAllMembers() {
        return membersRepository.findAll();
    }

    // Get member by id
    public Optional<Member> getMemberById(int id) {
        return membersRepository.findById(id);
    }

    // Update member
    public Member updateMember(int id, Member memberDetails) {
        if (membersRepository.existsById(id)) {
            memberDetails.setId(id);
            return membersRepository.save(memberDetails);
        }
        return null;
    }

    // Delete member
    public void deleteMember(int id) {
        membersRepository.deleteById(id);
    }

    public Optional<Member> checkLogin(String email, String password) {
        Optional<Member> memberOpt = membersRepository.findByEmail(email);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            // So sánh mật khẩu trực tiếp
            if (password.equals(member.getPassword())) {
                return Optional.of(member);  // Return member if password matches
            }
        }
        return Optional.empty();  // Return empty if login fails (either email or password is incorrect)
    }

}