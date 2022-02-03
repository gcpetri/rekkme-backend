package com.rekkme.data.repository;

import java.util.UUID;

import com.rekkme.data.entity.RekResult;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RekResultRepository extends JpaRepository<RekResult, UUID> { 
}