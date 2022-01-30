package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultNotificationDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private Long rekResultId;
    private String title;
    private UserDto toUser;
    private Integer wager;
    private Integer result;
    private Integer diff;
    private String createdOn;


    public Long getRekResultId() {
        return this.rekResultId;
    }

    public void setRekResultId(Long rekResultId) {
        this.rekResultId = rekResultId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getToUser() {
        return this.toUser;
    }

    public void setToUser(UserDto toUser) {
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
