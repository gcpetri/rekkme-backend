package com.rekkme.data.repository;

import java.util.UUID;

import com.rekkme.data.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> { 

    public Category findByNameIgnoreCase(String name);
}
