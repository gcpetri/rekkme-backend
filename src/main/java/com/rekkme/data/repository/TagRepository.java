package com.rekkme.data.repository;

import com.rekkme.data.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> { 
}
