package com.designpattern.model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phoneNo;
    private String role;
    private Timestamp createdAt;
    private Timestamp loginDate;

    // Default Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(int userId, String userName, String email, String password,
                String address, String phoneNo, String role,
                Timestamp createdAt, Timestamp loginDate) {

        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNo = phoneNo;
        this.role = role;
        this.createdAt = createdAt;
        this.loginDate = loginDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId +
                ", userName=" + userName +
                ", email=" + email +
                ", phoneNo=" + phoneNo +
                ", role=" + role + "]";
    }
}