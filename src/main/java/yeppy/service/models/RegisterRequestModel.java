package yeppy.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestModel {
    private String username;
    private String password;

    public RegisterRequestModel() {

    }

    @JsonCreator
    public RegisterRequestModel(@JsonProperty(value = "username", required = true)String email, @JsonProperty(value = "password", required = true)String password) {
        this.username = email;
        this.password = password;
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
}