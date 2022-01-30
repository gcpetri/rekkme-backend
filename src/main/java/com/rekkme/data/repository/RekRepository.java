package com.rekkme.data.repository;

import java.util.List;

import com.rekkme.data.entity.Rek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RekRepository extends JpaRepository<Rek, Long> { 

    @Query(value = "SELECT * FROM REKS r WHERE r.TO_USER_ID = ?1", nativeQuery = true)
    List<Rek> getReksTo(Long userId);
}
