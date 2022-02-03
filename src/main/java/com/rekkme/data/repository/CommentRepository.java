package com.rekkme.data.repository;

import java.util.UUID;

import com.rekkme.data.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> { 
}
