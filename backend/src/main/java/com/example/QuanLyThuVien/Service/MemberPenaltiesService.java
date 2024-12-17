package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.PenaltyDetailsDto;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Repo.MemberPenaltiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
}
