package com.example.QLTV.Repository;

import com.example.QLTV.Entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Integer> {
}