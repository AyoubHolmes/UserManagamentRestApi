package com.aboulbaz.UserManagementRestAPIAssignment.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String city;
    @Pattern(regexp = "[A-Z]{2}")
    private String country;
    private String avatar;
    private String company;
    private String jobPosition;
    @Pattern(regexp = "(\\+2126|06)[0-9]{8}")
    private String mobile;
    @Column(unique = true)
    private String username;
    @Email(message = "Email Not Correct")
    @Column(unique = true)
    private String email;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;

    /* ************ GETTERS **************** */

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCompany() {
        return company;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleEnum getRole() {
        return role;
    }

    /* ************ SETTERS **************** */

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
