package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RekDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long rekId;
    private String url;
    private String content;
	private Integer wager;
    private UserDto toUser;
    private UserDto fromUser;
    private CategoryDto category;
    private String createdOn;
    private RekResultDto rekResult;
    private List<CommentDto> comments;
    private Set<TagDto> tags = new HashSet<>();


    public Long getRekId() {
        return this.rekId;
    }

    public void setRekId(Long rekId) {
        this.rekId = rekId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWager() {
        return this.wager;
    }

    public void setWager(Integer wager) {
        this.wager = wager;
    }

    public UserDto getToUser() {
        return this.toUser;
    }

    public void setToUser(UserDto toUser) {
        this.toUser = toUser;
    }

    public UserDto getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(UserDto fromUser) {
        this.fromUser = fromUser;
    }

    public CategoryDto getCategory() {
        return this.category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }

    public RekResultDto getRekResult() {
        return this.rekResult;
    }

    public void setRekResult(RekResultDto rekResult) {
        this.rekResult = rekResult;
    }

    public List<CommentDto> getComments() {
        return this.comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Set<TagDto> getTags() {
        return this.tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

}
