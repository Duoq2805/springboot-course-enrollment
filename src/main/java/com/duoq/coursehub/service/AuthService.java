package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.auth.AuthResponse;
import com.duoq.coursehub.dto.auth.LoginRequest;
import com.duoq.coursehub.dto.auth.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest req);
    AuthResponse login(LoginRequest request);
}
