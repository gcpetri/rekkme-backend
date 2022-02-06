package com.rekkme.data.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name="USER_ID", columnDefinition = "uuid", updatable=false)
    private UUID userId;

    @Column(name="USERNAME", nullable=false, unique=true)
    @NotBlank(message="Username is required")
    private String username;

    @Column(name="FIRST_NAME", nullable=false)
	private String firstName;

    @Column(name="LAST_NAME", nullable=false)
    private String lastName;

    @Column(name="EMAIL", nullable=false, unique=true)
    private String email;

    @Column(name="REK_POINTS")
    private Integer rekPoints = 0;

    @Column(name="KOS")
    private Integer kos = 0;

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable(name="USER_FRIENDS",
        joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
        inverseJoinColumns=@JoinColumn(name="FRIEND_ID", referencedColumnName="USER_ID"))
    private Set<User> friends = new HashSet<>();

    @Column(name="IMAGE_URL")
    private String imageUrl;

    @Column(name="LAST_LOGIN")
    private LocalDateTime lastLogin;
}

