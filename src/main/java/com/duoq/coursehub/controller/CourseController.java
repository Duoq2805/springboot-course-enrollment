package com.duoq.coursehub.controller;

import com.duoq.coursehub.dto.course.CourseCreateRequest;
import com.duoq.coursehub.dto.course.CourseResponse;
import com.duoq.coursehub.dto.course.CourseUpdateRequest;
import com.duoq.coursehub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponse createCourse(@RequestBody CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourse(@PathVariable Long id,
                                       @RequestBody CourseUpdateRequest request) {
        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    public CourseResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
