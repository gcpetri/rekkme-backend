package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RekDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private UUID rekId;
    private String url;
    private String description;
	private Integer wager;
    private FriendDto toUser;
    private FriendDto fromUser;
    private CategoryDto category;
    private String createdOn;
    private RekResultDto rekResult;
    private List<CommentDto> comments;
    private Set<TagDto> tags = new HashSet<>();
    private String imageUrl;
    private String artist;
    private String location;
    private String title;

    public UUID getRekId() {
        return this.rekId;
    }

    public void setRekId(UUID rekId) {
        this.rekId = rekId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWager() {
        return this.wager;
    }

    public void setWager(Integer wager) {
        this.wager = wager;
    }

    public FriendDto getToUser() {
        return this.toUser;
    }

    public void setToUser(FriendDto toUser) {
        this.toUser = toUser;
    }

    public FriendDto getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(FriendDto fromUser) {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
