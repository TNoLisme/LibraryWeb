package com.example.QLTV.Service;

import com.example.QLTV.Entity.Members;
import com.example.QLTV.Repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MembersService {

    @Autowired
    private MembersRepository membersRepository;

    // Create or update a member
    public Members saveMember(Members member) {
        return membersRepository.save(member);
    }

    // Get all members
    public List<Members> getAllMembers() {
        return membersRepository.findAll();
    }

    // Get member by id
    public Optional<Members> getMemberById(int id) {
        return membersRepository.findById(id);
    }

    // Update member
    public Members updateMember(int id, Members memberDetails) {
        if (membersRepository.existsById(id)) {
            memberDetails.setMemberId(id);
            return membersRepository.save(memberDetails);
        }
        return null;
    }

    // Delete member
    public void deleteMember(int id) {
        membersRepository.deleteById(id);
    }
}
