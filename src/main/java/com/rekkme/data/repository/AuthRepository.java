package com.rekkme.data.repository;

import java.util.UUID;

import com.rekkme.data.entity.Auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepository extends JpaRepository<Auth, UUID> { 

    @Query(value = "SELECT * FROM AUTH a WHERE a.USER_ID = ?1", nativeQuery = true)
    Auth findByUserId(UUID userId);
}
