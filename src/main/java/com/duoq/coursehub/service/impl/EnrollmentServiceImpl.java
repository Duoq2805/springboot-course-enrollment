package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.enrollment.EnrollmentCheckResponse;
import com.duoq.coursehub.dto.enrollment.EnrollmentCreateRequest;
import com.duoq.coursehub.dto.enrollment.EnrollmentResponse;
import com.duoq.coursehub.enums.CourseStatus;
import com.duoq.coursehub.enums.EnrollmentStatus;
import com.duoq.coursehub.enums.Role;
import com.duoq.coursehub.exception.ConflictException;
import com.duoq.coursehub.exception.NotFoundException;
import com.duoq.coursehub.exception.BadRequestException;
import com.duoq.coursehub.model.Course;
import com.duoq.coursehub.model.Enrollment;
import com.duoq.coursehub.model.User;
import com.duoq.coursehub.repository.CourseRepo;
import com.duoq.coursehub.repository.EnrollmentRepo;
import com.duoq.coursehub.repository.UserRepo;
import com.duoq.coursehub.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepo enrollmentRepo;
    private final UserRepo userRepo;
    private final CourseRepo courseRepo;

    @Override
    public EnrollmentResponse enroll(EnrollmentCreateRequest request) {

        User student = getStudentOrThrow(request.getStudentId());
        Course course = getAvailableCourseOrThrow(request.getCourseId());

        ensureNotEnrolled(student.getId(), course.getId());

        Enrollment enrollment = buildEnrollment(student, course);
        Enrollment saved = enrollmentRepo.save(enrollment);

        return toResponse(saved);
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId, String status) {
        List<Enrollment> enrollments;

        if (status == null) {
            enrollments = enrollmentRepo.findByUser_Id(studentId);
        } else {
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.fromString(status);
            enrollments = enrollmentRepo.findByUser_IdAndStatus(studentId, enrollmentStatus);
        }

        return enrollments.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public EnrollmentCheckResponse checkEnrollment(Long studentId, Long courseId) {
        return enrollmentRepo.findByUser_IdAndCourse_Id(studentId, courseId)
                .map(this::toCheckResponse)
                .orElseGet(() -> new EnrollmentCheckResponse(false, null));
    }

    @Override
    public EnrollmentResponse approveEnrollment(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() ->
                        new NotFoundException("Enrollment not found"));

        if (enrollment.getStatus() != EnrollmentStatus.PENDING) {
            throw new BadRequestException("Enrollment is not pending");
        }

        enrollment.setStatus(EnrollmentStatus.APPROVED);
        enrollment.setApprovedAt(LocalDateTime.now());
        return toResponse(enrollmentRepo.save(enrollment));
    }

    // TODO: Integrate payment service
    // - Only approve enrollment after successful payment
    // - Support webhook from payment gateway (VNPay, Stripe, PayPal)
    // - Auto-cancel enrollment if payment timeout



    // ================= HELPER METHODS =================

    private User getStudentOrThrow(Long studentId) {
        User user = userRepo.findById(studentId)
                .orElseThrow(() ->
                        new NotFoundException("Student not found with id: " + studentId));

        if (user.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only students can enroll in courses");
        }
        return user;
    }

    private Course getAvailableCourseOrThrow(Long courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() ->
                        new NotFoundException("Course not found with id: " + courseId));

        if (course.getStatus() == CourseStatus.DELETED
                || course.getStatus() == CourseStatus.ARCHIVED) {
            throw new BadRequestException("Course is not available for enrollment");
        }
        return course;
    }

    private void ensureNotEnrolled(Long studentId, Long courseId) {
        if (enrollmentRepo.existsByUser_IdAndCourse_Id(studentId, courseId)) {
            throw new ConflictException("Student already enrolled in this course");
        }
    }

    private Enrollment buildEnrollment(User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.PENDING);
        return enrollment;
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse res = new EnrollmentResponse();
        res.setId(enrollment.getId());
        res.setStudentId(enrollment.getUser().getId());
        res.setCourseId(enrollment.getCourse().getId());
        res.setStatus(enrollment.getStatus().name());
        return res;
    }

    private EnrollmentCheckResponse toCheckResponse(Enrollment e) {
        return new EnrollmentCheckResponse(true, e.getStatus().name());
    }
}


