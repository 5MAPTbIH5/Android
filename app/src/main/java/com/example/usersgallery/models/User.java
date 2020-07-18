package com.example.usersgallery.models;

import java.io.Serializable;

public class User implements Serializable {

    Integer id;
    String login = "";
    String password = "";

    public User() {
    }

    public User(Integer id, String login, String password) {
        this.id = id;
        if(login != null){
            this.login = login;
        }
        if(password != null){
            this.password = password;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
