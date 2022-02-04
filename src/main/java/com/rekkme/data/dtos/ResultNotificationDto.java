package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ResultNotificationDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private UUID rekResultId;
    private String title;
    private FriendDto toUser;
    private Integer wager;
    private Integer result;
    private Integer diff;
    private String createdOn;


    public UUID getRekResultId() {
        return this.rekResultId;
    }

    public void setRekResultId(UUID rekResultId) {
        this.rekResultId = rekResultId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FriendDto getToUser() {
        return this.toUser;
    }

    public void setToUser(FriendDto toUser) {
        this.toUser = toUser;
    }

    public Integer getWager() {
        return this.wager;
    }

    public void setWager(Integer wager) {
        this.wager = wager;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getDiff() {
        return this.diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }

}
