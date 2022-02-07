package com.rekkme.dtos.responses;

import com.rekkme.dtos.entity.UserDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

    private UserDto session;
}
