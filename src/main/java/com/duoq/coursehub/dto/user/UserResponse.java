package com.duoq.coursehub.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}
