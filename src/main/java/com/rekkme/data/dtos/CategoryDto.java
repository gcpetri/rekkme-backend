package com.rekkme.data.dtos;

import java.util.UUID;

public class CategoryDto {
    private UUID categoryId;
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
