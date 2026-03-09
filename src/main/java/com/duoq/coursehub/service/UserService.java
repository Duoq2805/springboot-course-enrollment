package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    UserResponse updateStatus(Long id, String status);
}
