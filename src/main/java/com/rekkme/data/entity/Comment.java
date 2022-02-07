package com.rekkme.data.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="COMMENTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue
    @Column(name="COMMENT_ID", columnDefinition = "uuid", updatable=false)
    private UUID commentId;

    @Column(name="MESSAGE", updatable=false)
    private String message;

    @Column(name="CREATED_ON", updatable=false)
    private LocalDateTime createdOn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REK_ID", referencedColumnName="REK_ID")
    private Rek rek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
    private User user;
}
