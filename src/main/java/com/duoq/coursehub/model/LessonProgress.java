package com.duoq.coursehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "LessonProgress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"UserID", "LessonID"})
        }
)
@Data
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "LessonID", nullable = false)
    private Lesson lesson;

    @Column(name = "Completed")
    private Boolean completed = false;

    @Column(name = "CompletedAt")
    private LocalDateTime completedAt;
}
