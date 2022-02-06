package com.rekkme.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String avatar;
}
