package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.course.CourseCreateRequest;
import com.duoq.coursehub.dto.course.CourseResponse;
import com.duoq.coursehub.dto.course.CourseUpdateRequest;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseCreateRequest request);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long courseId);
    CourseResponse updateCourse(Long id, CourseUpdateRequest request);
    CourseResponse deleteCourse(Long id);
}
