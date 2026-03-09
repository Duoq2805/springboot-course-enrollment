package com.duoq.coursehub.repository;

import com.duoq.coursehub.enums.EnrollmentStatus;
import com.duoq.coursehub.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepo extends JpaRepository<Enrollment,Long> {
    boolean existsByUser_IdAndCourse_Id(Long userId, Long courseId);
    List<Enrollment> findByUser_IdAndStatus(Long userId, EnrollmentStatus status);
    List<Enrollment> findByUser_Id(Long studentId);
    Optional<Enrollment> findByUser_IdAndCourse_Id(Long userId, Long courseId);

}
