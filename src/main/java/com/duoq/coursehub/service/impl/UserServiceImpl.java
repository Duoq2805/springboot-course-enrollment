package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.user.UserResponse;
import com.duoq.coursehub.enums.UserStatus;
import com.duoq.coursehub.exception.NotFoundException;
import com.duoq.coursehub.model.User;
import com.duoq.coursehub.repository.UserRepo;
import com.duoq.coursehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserResponse getById(Long id) {
        return toResponse(getUserOrThrow(id));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateStatus(Long id, String status) {
        User user = getUserOrThrow(id);
        user.setStatus(UserStatus.fromString(status));
        return toResponse(userRepo.save(user));
    }


    // ================= HELPER METHODS =================

    private UserResponse toResponse(User u) {
        UserResponse res = new UserResponse();
        res.setId(u.getId());
        res.setEmail(u.getEmail());
        res.setFullName(u.getFullName());
        res.setRole(u.getRole().name());
        res.setStatus(u.getStatus().name());
        res.setCreatedAt(u.getCreatedAt());
        return res;
    }

    private User getUserOrThrow(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
