package com.duoq.coursehub.dto.enrollment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String status;
    private LocalDateTime enrolledAt;
}
