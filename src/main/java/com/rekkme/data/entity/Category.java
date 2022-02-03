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
@Table(name="CATEGORIES")
@DynamicUpdate
public class Category {

    @Id
    @GeneratedValue
    @Column(name="CATEGORY_ID", columnDefinition = "uuid", updatable=false)
    private UUID categoryId;

    @Column(name="NAME", nullable=false, unique=true)
    @NotBlank(message="Name is required")
    private String name;


    public UUID getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
