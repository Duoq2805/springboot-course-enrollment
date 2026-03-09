package com.duoq.coursehub.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollmentCheckResponse {
    private boolean enrolled;
    private String status;
}
