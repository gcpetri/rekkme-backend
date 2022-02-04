package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CommentDto {
    
    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private UUID commentId;
    private String message;
    private String createdOn;
    private FriendDto user;


    public UUID getCommentId() {
        return this.commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }

    public FriendDto getUser() {
        return this.user;
    }

    public void setUser(FriendDto user) {
        this.user = user;
    }

}
