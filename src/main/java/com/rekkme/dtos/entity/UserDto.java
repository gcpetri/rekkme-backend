package com.rekkme.dtos.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private UUID userId;
    private String username;
	private String firstName;
    private String lastName;
    private String email;
    private Integer rekPoints = 0;
    private Integer kos = 0;
    private String imageUrl;
    private String lastLogin;
    private Boolean isPublic;
    private Integer numFriends = 0;
    private Integer numCrowd = 0;

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = dateFormatter.format(lastLogin);
    }
}
