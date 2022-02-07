package com.rekkme.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String avatar;
    private Boolean isPublic;
}
