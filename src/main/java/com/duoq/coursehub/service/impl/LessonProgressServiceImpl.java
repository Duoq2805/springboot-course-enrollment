package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.lessonProgress.LessonProgressResponse;
import com.duoq.coursehub.exception.NotFoundException;
import com.duoq.coursehub.model.Lesson;
import com.duoq.coursehub.model.LessonProgress;
import com.duoq.coursehub.model.User;
import com.duoq.coursehub.repository.LessonProgressRepo;
import com.duoq.coursehub.repository.LessonRepo;
import com.duoq.coursehub.repository.UserRepo;
import com.duoq.coursehub.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonProgressServiceImpl implements LessonProgressService {

    private final LessonProgressRepo lessonProgressRepo;
    private final LessonRepo lessonRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public LessonProgressResponse markCompleted(Long userId, Long lessonId) {
        // Kiểm tra user và lesson tồn tại
        User user = getUserOrThrow(userId);
        Lesson lesson = getLessonOrThrow(lessonId);

        // Tìm progress, nếu chưa có thì tạo mới
        LessonProgress progress = lessonProgressRepo
                .findByUser_IdAndLesson_Id(userId, lessonId)
                .orElse(new LessonProgress());

        // Set dữ liệu
        progress.setUser(user);
        progress.setLesson(lesson);

        // Nếu chưa completed thì set completed và set thời gian
        if (!progress.getCompleted()) {
            progress.setCompleted(true);
            progress.setCompletedAt(LocalDateTime.now());
        }

        // Lưu và trả về
        return toResponse(lessonProgressRepo.save(progress));
    }

    @Override
    public List<LessonProgressResponse> getProgressByUser(Long userId) {
        return lessonProgressRepo.findByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ================= HELPER METHODS =================

    private LessonProgressResponse toResponse(LessonProgress p) {
        LessonProgressResponse res = new LessonProgressResponse();
        res.setId(p.getId());
        res.setUserId(p.getUser().getId());
        res.setLessonId(p.getLesson().getId());
        res.setCompleted(p.getCompleted()); // SỬA: dùng p.getCompleted()
        res.setCompletedAt(p.getCompletedAt());
        return res; // SỬA: return res, không phải object mới
    }

    private Lesson getLessonOrThrow(Long lessonId) {
        return lessonRepo.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found with id: " + lessonId));
    }

    private User getUserOrThrow(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }
}