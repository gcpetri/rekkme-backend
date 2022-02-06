package com.rekkme.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="AUTH")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    
    @Id
    @GeneratedValue
    @Column(name="AUTH_ID", columnDefinition = "uuid", updatable=false)
    private UUID authId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
    private User user;

    @Column(name="PASSWORD", nullable=false)
    private String password;
}
