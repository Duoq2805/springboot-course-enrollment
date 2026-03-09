package com.duoq.coursehub.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName; // THÊM DÒNG NÀY
}