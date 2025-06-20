package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class User {
    private String username;
    private String password;
    private LocalDate DateOfBirth ;
    User(String username,String password,LocalDate DateOfBirth){
        this.DateOfBirth=DateOfBirth;
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(LocalDate dateofbirth) {
        DateOfBirth = dateofbirth;
    }

    // public boolean login (String username,String password){
    //     if (username==null||password==null)
    //         return false;
    //         //if username correct and password wrong
    //         //if username wrong and password correct
    //     else
    //         return true;


}
//public void CreateNewAccount (String username ,String password){
//  this.username=username;
//this.password=password;

//};
