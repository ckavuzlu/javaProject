package com.example.myapplication;

import com.google.firebase.firestore.auth.User;

public class UserInfo {

    String UserUid , UserName;

    public UserInfo(){}

    public UserInfo(String userUid, String userName){
        UserUid = userUid ;
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }
}
