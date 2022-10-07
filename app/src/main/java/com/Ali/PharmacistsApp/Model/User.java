package com.Ali.PharmacistsApp.Model;

public class User {
    private String name;
    private String email;
    private String api_token;
    private String updated_at;
    private String created_at;
    private String image;
    private int id;
    private String success;

    public User(String name, String email, String api_token, String updated_at, String created_at, String image, int id,String success) {
        this.name = name;
        this.email = email;
        this.api_token = api_token;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.image = image;
        this.id = id;
        this.success=success;
    }

// Getter Methods

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getApi_token() {
        return api_token;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
