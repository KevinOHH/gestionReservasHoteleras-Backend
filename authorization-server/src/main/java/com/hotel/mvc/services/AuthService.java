package com.hotel.mvc.services;

import com.hotel.mvc.dto.LoginRequest;
import com.hotel.mvc.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}
