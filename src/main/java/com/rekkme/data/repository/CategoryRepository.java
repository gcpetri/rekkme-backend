package com.rekkme.data.repository;

import com.rekkme.data.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { 
}
