package com.rekkme.dtos.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = dateFormatter.format(lastLogin);
    }
}
