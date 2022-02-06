package com.rekkme.dtos.responses;

import com.rekkme.dtos.entity.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

    private UserDto session;
}
