package com.duoq.coursehub.model;

import com.duoq.coursehub.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID")
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private CourseStatus status;

    @ManyToOne
    @JoinColumn(name = "InstructorID", nullable = false)
    private User instructor;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
