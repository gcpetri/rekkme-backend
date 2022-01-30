package com.rekkme.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @Column(name="REK_RESULT_ID", updatable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long rekResultId;

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


    public Long getRekResultId() {
        return this.rekResultId;
    }

    public void setRekResultId(Long rekResultId) {
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
