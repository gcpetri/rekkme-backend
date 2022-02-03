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

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="USERS")
@DynamicUpdate
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

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRekPoints() {
        return this.rekPoints;
    }

    public void setRekPoints(Integer rekPoints) {
        this.rekPoints = rekPoints;
    }

    public Integer getKos() {
        return this.kos;
    }

    public void setKos(Integer kos) {
        this.kos = kos;
    }

    public Set<User> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

}

