package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.auth.AuthResponse;
import com.duoq.coursehub.dto.auth.LoginRequest;
import com.duoq.coursehub.dto.auth.RegisterRequest;
import com.duoq.coursehub.enums.Role;
import com.duoq.coursehub.enums.UserStatus;
import com.duoq.coursehub.exception.BadRequestException;
import com.duoq.coursehub.model.User;
import com.duoq.coursehub.repository.UserRepo;
import com.duoq.coursehub.security.CustomUserDetails;
import com.duoq.coursehub.security.jwt.JwtUtil;
import com.duoq.coursehub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // THÊM DÒNG NÀY

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName()); // THÊM: lưu fullName từ request
        user.setRole(Role.STUDENT);
        user.setStatus(UserStatus.ACTIVE);

        userRepo.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        // 1️⃣ Xác thực bằng AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        // 2️⃣ Lấy user details từ authentication
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 3️⃣ Tìm user từ database để generate token
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        // 4️⃣ Generate JWT token
        String token = jwtUtil.generateToken(user);

        // 5️⃣ Trả về response
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}