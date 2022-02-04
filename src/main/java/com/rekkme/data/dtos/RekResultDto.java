package com.rekkme.data.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class RekResultDto {

    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private UUID rekResultId;
	private Integer result = 0;
	private Boolean ko = false;
    private String createdOn;


    public UUID getRekResultId() {
        return this.rekResultId;
    }

    public void setRekResultId(UUID rekResultId) {
        this.rekResultId = rekResultId;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Boolean isKo() {
        return this.ko;
    }

    public Boolean getKo() {
        return this.ko;
    }

    public void setKo(Boolean ko) {
        this.ko = ko;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = dateFormatter.format(createdOn);
    }

}
