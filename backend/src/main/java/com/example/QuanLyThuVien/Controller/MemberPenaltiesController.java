package com.example.QuanLyThuVien.Controller;

import com.example.QuanLyThuVien.DTO.MemberPenaltiesDTO;
import com.example.QuanLyThuVien.DTO.PenaltyDetailsDto;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Entity.MemberPenalties.PaidStatus;
import com.example.QuanLyThuVien.Service.MemberPenaltiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import javax.management.ServiceNotFoundException;

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
    @GetMapping("/member/{memberId}")
    public List<MemberPenalties> getPenaltiesByMember(@PathVariable int memberId) {
        return memberPenaltiesService.findAllByMemberIdWithDetails(memberId);
    }
    @PatchMapping("/{penaltyRecordID}/paid-status")
    public ResponseEntity<MemberPenaltiesDTO> updatePaidStatus(
            @PathVariable int penaltyRecordID,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newStatus = requestBody.get("paidStatus").toLowerCase();
            PaidStatus statusEnum = PaidStatus.valueOf(newStatus);

            MemberPenalties updatedPenalty = memberPenaltiesService.updatePaymentStatus(penaltyRecordID, statusEnum);

            // Chuyển đổi sang DTO
            MemberPenaltiesDTO dto = new MemberPenaltiesDTO();
            dto.setPenaltyRecordID(updatedPenalty.getPenaltyRecordID());
            dto.setPaidStatus(updatedPenalty.getPaidStatus().name());

            return ResponseEntity.ok(dto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }





}
