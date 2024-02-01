package com.example.flightsearchapi.dtos;

public class AuthenticateRequestDto {
    private String username;
    private String password;

    public AuthenticateRequestDto() {
    }

    public AuthenticateRequestDto(String username, String password) {
        this.username = username;
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
