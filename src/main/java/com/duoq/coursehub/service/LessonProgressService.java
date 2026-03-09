package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.lessonProgress.LessonProgressResponse;

import java.util.List;

public interface LessonProgressService {
    LessonProgressResponse markCompleted(Long userId, Long lessonId);
    List<LessonProgressResponse> getProgressByUser(Long userId);
}
