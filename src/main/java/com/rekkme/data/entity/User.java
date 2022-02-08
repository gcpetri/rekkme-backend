package com.rekkme.data.entity;

import java.time.LocalDateTime;
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
@Table(name="USERS")
@Getter
@Setter
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

    @Column(name="IMAGE_URL")
    private String imageUrl;

    @Column(name="IS_PUBLIC", nullable = false)
    private Boolean isPublic = true;

    @Column(name="LAST_LOGIN")
    private LocalDateTime lastLogin;
}

