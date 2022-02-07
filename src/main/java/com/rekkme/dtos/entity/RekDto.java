package com.rekkme.dtos.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RekDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private UUID rekId;
    private String url;
    private String description;
	private Integer wager;
    private UserDto toUser;
    private UserDto fromUser;
    private CategoryDto category;
    private String createdOn;
    private RekResultDto rekResult;
    private List<CommentDto> comments;
    private Set<TagDto> tags = new HashSet<>();
    private String imageUrl;
    private String artist;
    private String location;
    private String title;
    private int numLikes;
    private Boolean liked = false;

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }
}
