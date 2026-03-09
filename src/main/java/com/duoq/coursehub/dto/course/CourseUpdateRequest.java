package com.duoq.coursehub.dto.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequest {
    private String title;
    private String description;
    private String status; // DRAFT / PUBLISHED / ARCHIVED
}
