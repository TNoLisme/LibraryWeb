package com.example.QuanLyThuVien.Controller;

import com.example.QuanLyThuVien.DTO.PenaltyDetailsDto;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Service.MemberPenaltiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/penalties")
public class MemberPenaltiesController {

    private final MemberPenaltiesService memberPenaltiesService;

    @Autowired
    public MemberPenaltiesController(MemberPenaltiesService memberPenaltiesService) {
        this.memberPenaltiesService = memberPenaltiesService;
    }

    @GetMapping
    public List<MemberPenalties> getPenalties() {
        return memberPenaltiesService.getAllPenalties();
    }
}
