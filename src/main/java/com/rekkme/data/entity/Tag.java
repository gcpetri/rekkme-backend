package com.rekkme.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TAGS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    @Column(name="TAG_ID", columnDefinition = "uuid", updatable=false)
    private UUID tagId;

    @Column(name="NAME", nullable=false, unique=true)
    @NotBlank(message="Name is required")
    private String name;
}
