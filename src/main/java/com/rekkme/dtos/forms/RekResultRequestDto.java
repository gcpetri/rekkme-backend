package com.rekkme.dtos.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RekResultRequestDto {
    
    private Integer result;
    private Boolean ko = false;
}
