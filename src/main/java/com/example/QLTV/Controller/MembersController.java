package com.example.QLTV.Controller;

import com.example.QLTV.Entity.Members;
import com.example.QLTV.Service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @PostMapping
    public ResponseEntity<Members> createMember(@RequestBody Members member) {
        Members savedMember = membersService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Members> getAllMembers() {
        return membersService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Members> getMemberById(@PathVariable int id) {
        Optional<Members> member = membersService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Members> updateMember(@PathVariable int id, @RequestBody Members memberDetails) {
        Members updatedMember = membersService.updateMember(id, memberDetails);
        return updatedMember != null ? ResponseEntity.ok(updatedMember) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        membersService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
