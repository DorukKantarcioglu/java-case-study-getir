package com.getir.readingisgood.service;

import com.getir.readingisgood.request.LoginRequest;
import com.getir.readingisgood.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
}
