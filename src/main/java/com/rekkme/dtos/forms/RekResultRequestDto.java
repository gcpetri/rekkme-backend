package com.rekkme.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RekResultRequestDto {
    
    private Integer result;
    private Boolean ko = false;
}
