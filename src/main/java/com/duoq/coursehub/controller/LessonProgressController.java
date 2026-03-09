package com.duoq.coursehub.controller;

import com.duoq.coursehub.dto.lessonProgress.LessonProgressResponse;
import com.duoq.coursehub.security.CustomUserDetails;
import com.duoq.coursehub.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    @PostMapping("/lesson/{lessonId}")
    public LessonProgressResponse markLessonCompleted(
            @AuthenticationPrincipal CustomUserDetails currentUser, // CÁCH 1: Lấy từ SecurityContext
            @PathVariable Long lessonId) {

        // Lấy userId từ token, KHÔNG nhận từ request parameter
        Long userId = currentUser.getId();
        return lessonProgressService.markCompleted(userId, lessonId);
    }

    @GetMapping("/user/me") // SỬA: /user/{id} -> /user/me
    public List<LessonProgressResponse> getMyLessonProgress(
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        return lessonProgressService.getProgressByUser(currentUser.getId());
    }

    // CÁCH 2: Nếu muốn admin xem được của người khác
    @GetMapping("/user/{userId}")
    public List<LessonProgressResponse> getUserLessonProgress(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long userId) {

        // Kiểm tra nếu không phải admin và không phải user của chính mình
        if (!currentUser.getRole().equals("ADMIN") && !currentUser.getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to view this user's progress");
        }

        return lessonProgressService.getProgressByUser(userId);
    }
}