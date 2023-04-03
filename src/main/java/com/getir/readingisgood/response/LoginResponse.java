package com.getir.readingisgood.response;

public class LoginResponse {
    private final String username;

    private final String token;

    private final String type;

    public LoginResponse(String username, String token) {
        this.username = username;
        this.token = token;
        type = "Bearer";
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
