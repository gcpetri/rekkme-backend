package com.rekkme.dtos.responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.rekkme.dtos.entity.UserDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultNotificationDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private UUID rekResultId;
    private String title;
    private UserDto toUser;
    private Integer wager;
    private Integer result;
    private Integer diff;
    private String createdOn;

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }
}
