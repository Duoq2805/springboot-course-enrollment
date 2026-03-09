package com.duoq.coursehub.controller;

import com.duoq.coursehub.dto.lesson.LessonCreateRequest;
import com.duoq.coursehub.dto.lesson.LessonResponse;
import com.duoq.coursehub.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public LessonResponse createLesson(@RequestBody LessonCreateRequest request) {
        return lessonService.create(request);
    }

    @GetMapping("/course/{courseId}")
    public List<LessonResponse> getByCourse(@PathVariable Long courseId) {
        return lessonService.getByCourse(courseId);
    }
}
