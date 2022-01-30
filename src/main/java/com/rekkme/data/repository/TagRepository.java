package com.rekkme.data.repository;

import com.rekkme.data.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByNameIgnoreCase(String name);

    @Modifying
    @Query(value = "INSERT INTO REK_TAG_LINKS (REK_ID, TAG_ID) VALUES (?1, ?2)", nativeQuery = true)
    public void addRekTagLink(Long rekId, Long tagId);
}
