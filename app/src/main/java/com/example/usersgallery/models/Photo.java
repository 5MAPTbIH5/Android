package com.example.usersgallery.models;

import java.io.Serializable;

public class Photo implements Serializable {
    Integer id;
    String title = "";
    String description = "";
    String path = "";
    Integer idUser;

    public Photo() {
    }

    public Photo(Integer id, String title, String description, String path, Integer idUser) {
        this.id = id;
        if(title != null){
            this.title = title;
        }
        if(description != null){
            this.description = description;
        }
        if(path != null){
            this.path = path;
        }
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
