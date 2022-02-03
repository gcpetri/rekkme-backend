package com.rekkme.data.repository;

import java.util.UUID;

import com.rekkme.data.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Tag findByNameIgnoreCase(String name);

    @Modifying
    @Query(value = "INSERT INTO REK_TAG_LINKS (REK_ID, TAG_ID) VALUES (?1, ?2)", nativeQuery = true)
    public void addRekTagLink(UUID rekId, UUID tagId);
}
