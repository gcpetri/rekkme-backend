package com.rekkme.data.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="REK_RESULTS")
@DynamicUpdate
public class RekResult {

    @Id
    @GeneratedValue
    @Column(name="REK_RESULT_ID", columnDefinition = "uuid", updatable=false)
    private UUID rekResultId;

    @Column(name="RESULT")
	private Integer result = 0;

    @Column(name="KO", nullable=false)
	private Boolean ko = false;

    @Column(name="CREATED_ON", updatable=false)
    private LocalDateTime createdOn;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REK_ID", referencedColumnName="REK_ID")
    private Rek rek;


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

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Rek getRek() {
        return this.rek;
    }

    public void setRek(Rek rek) {
        this.rek = rek;
    }

}
