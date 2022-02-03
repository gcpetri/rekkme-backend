package com.rekkme.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="TAGS")
@DynamicUpdate
public class Tag {

    @Id
    @GeneratedValue
    @Column(name="TAG_ID", columnDefinition = "uuid", updatable=false)
    private UUID tagId;

    @Column(name="NAME", nullable=false, unique=true)
    @NotBlank(message="Name is required")
    private String name;


    public UUID getTagId() {
        return this.tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
