package com.duoq.coursehub.dto.enrollment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentCreateRequest {
    private Long studentId;
    private Long courseId;
}
