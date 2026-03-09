package com.duoq.coursehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Lessons")
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LessonID")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)")
    private String content; // text hoặc video URL

    @Column(name = "OrderIndex")
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    private Course course;
}
