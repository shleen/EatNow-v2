package com.example.eatnow.eatnow;

public class Users {

    String userId;
    String userName;
    String userDeviceToken;

    public Users(){}


    public Users(String userId, String userDeviceToken){
        this.userId = userId;
        this.userDeviceToken = userDeviceToken;
    }

    public String getUserId() {
        return userId;
    }


    public String getUserDeviceToken() {
        return userDeviceToken;
    }
}
