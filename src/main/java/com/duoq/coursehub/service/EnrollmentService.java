package com.duoq.coursehub.service;

import com.duoq.coursehub.dto.enrollment.EnrollmentCheckResponse;
import com.duoq.coursehub.dto.enrollment.EnrollmentCreateRequest;
import com.duoq.coursehub.dto.enrollment.EnrollmentResponse;
import com.duoq.coursehub.enums.EnrollmentStatus;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentCreateRequest request);
    List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId, String status);
    EnrollmentCheckResponse checkEnrollment(Long studentId, Long courseId);
    EnrollmentResponse approveEnrollment(Long enrollmentId);
}
