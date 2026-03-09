package com.duoq.coursehub.model;

import com.duoq.coursehub.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "Enrollments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"UserID", "CourseID"})
        }
)
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnrollmentID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private EnrollmentStatus status;

    @Column(name = "EnrolledAt")
    private LocalDateTime enrolledAt;

    @PrePersist
    public void prePersist() {
        this.enrolledAt = LocalDateTime.now();
    }

    @Column(name = "ApprovedAt")
    private LocalDateTime approvedAt;
}
