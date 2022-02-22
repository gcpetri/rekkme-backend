package com.rekkme.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    
    String username;
    String message;
    String linkTo;
    String avatar;
}
