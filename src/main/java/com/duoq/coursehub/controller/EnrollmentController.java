package com.duoq.coursehub.controller;

import com.duoq.coursehub.dto.enrollment.EnrollmentCheckResponse;
import com.duoq.coursehub.dto.enrollment.EnrollmentCreateRequest;
import com.duoq.coursehub.dto.enrollment.EnrollmentResponse;
import com.duoq.coursehub.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponse enroll(@RequestBody EnrollmentCreateRequest request) {
        return enrollmentService.enroll(request);
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponse> getEnrollmentsByStudent(@PathVariable Long studentId,
                                                            @RequestParam(required = false) String status) {
        return enrollmentService.getEnrollmentsByStudent(studentId, status);
    }

    @GetMapping("/check")
    public EnrollmentCheckResponse checkEnrollment(@RequestParam Long studentId,
                                                   @RequestParam Long enrollmentId) {
        return enrollmentService.checkEnrollment(studentId, enrollmentId);
    }

    @PutMapping("/{id}/approve")
    public EnrollmentResponse approveEnrollment(@PathVariable Long id) {
        return enrollmentService.approveEnrollment(id);
    }

}
