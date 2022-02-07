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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="REK_RESULTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
