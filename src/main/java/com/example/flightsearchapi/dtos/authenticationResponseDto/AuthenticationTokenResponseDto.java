package com.example.flightsearchapi.dtos.authenticationResponseDto;

public class AuthenticationTokenResponseDto {
    String token;

    public AuthenticationTokenResponseDto(String token) {
        this.token = token;
    }

    public AuthenticationTokenResponseDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
