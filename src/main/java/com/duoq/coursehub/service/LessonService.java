package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.lesson.LessonCreateRequest;
import com.duoq.coursehub.dto.lesson.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse create(LessonCreateRequest request);
    List<LessonResponse> getByCourse(Long courseId);
}
