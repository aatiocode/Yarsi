package com.yarsi.yarsi.model;

/**
 * Created by Y700FROG on 30/11/2017.
 */

public class LoginPojo {
    private String userName;
    private String password;
    private String grant_type;

    public LoginPojo(String userName, String password, String grant_type) {
        this.userName = userName;
        this.password = password;
        this.grant_type = grant_type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

}
