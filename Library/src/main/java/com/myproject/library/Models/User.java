package com.myproject.library.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int Id;
    public String Name;
    public String Email;
    public String PasswordHash;
    public String Role;
    public Date DoB;
    public String ProfilePhoto;
    
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getPasswordHash() {
        return PasswordHash;
    }
    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }
    public String getRole() {
        return Role;
    }
    public void setRole(String role) {
        Role = role;
    }
    public Date getDoB() {
        return DoB;
    }
    public void setDoB(Date doB) {
        DoB = doB;
    }
    public String getProfilePhoto() {
        return ProfilePhoto;
    }
    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }
}
