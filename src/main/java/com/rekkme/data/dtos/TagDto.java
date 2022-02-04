package com.rekkme.data.dtos;

import java.util.UUID;

public class TagDto {
    
    private UUID tagId;
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
