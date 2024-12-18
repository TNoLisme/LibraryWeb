package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.PenaltyDetailsDto;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Entity.MemberPenalties.PaidStatus;
import com.example.QuanLyThuVien.Repo.MemberPenaltiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberPenaltiesService {

    private final MemberPenaltiesRepository memberPenaltiesRepository;

    @Autowired
    public MemberPenaltiesService(MemberPenaltiesRepository memberPenaltiesRepository) {
        this.memberPenaltiesRepository = memberPenaltiesRepository;
    }
    public List<MemberPenalties> getAllPenalties() {
        return memberPenaltiesRepository.findAllWithDetails();
    }
    public List<MemberPenalties> findAllByMemberIdWithDetails(int memberId) {
        return memberPenaltiesRepository.findAllByMemberIdWithDetails(memberId);
    }
    public MemberPenalties updatePaymentStatus(int penaltyRecordID, PaidStatus newStatus) throws Exception {
        Optional<MemberPenalties> penaltyOptional = memberPenaltiesRepository.findById(penaltyRecordID);

        if (penaltyOptional.isPresent()) {
            MemberPenalties penalty = penaltyOptional.get();
            penalty.setPaidStatus(newStatus); 
            return memberPenaltiesRepository.save(penalty); 
        } else {
            throw new Exception("Penalty record not found with ID: " + penaltyRecordID);
        }
    }

    
    
}
