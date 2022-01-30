package com.rekkme.data.dtos;

public class UserDto {
    private Long userId;
    private String username;
	private String firstName;
    private String lastName;
    private String email;
    private Integer rekPoints = 0;
    private Integer kos = 0;


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
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

}
