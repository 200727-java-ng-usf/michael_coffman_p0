package com.revature.models;

import java.util.Objects;

public class AppUser {

    // We make all the fields private, so they only be effected
    // by the getter / setter methods.
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private int id;
    private Role role;

    // No-args constructor
    public AppUser() {
        super();
    }

    // Constructor for AppUser during registration
    public AppUser(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Constructor for AppUser after role and id are assigned
    public AppUser(String firstName, String lastName, String username, String password, String email, int id, Role role) {
        this(firstName, lastName, username, password, email);
        this.id = id;
        this.role = role;
    }

    // Getters & Setters for AppUser
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Override equals() method, hashcode() method, & toString()
    // to complete setup of simple POJO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password) &&
                Objects.equals(email, appUser.email) &&
                role == appUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, email, id, role);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}



