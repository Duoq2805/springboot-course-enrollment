package com.duoq.coursehub.controller;

import com.duoq.coursehub.dto.user.UserResponse;
import com.duoq.coursehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/{id}/status")
    public UserResponse updateUserStatus(@PathVariable Long id,
                                         @RequestParam String status) {
        return userService.updateStatus(id, status);
    }
}
