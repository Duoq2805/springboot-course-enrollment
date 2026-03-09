package com.duoq.coursehub.repository;

import com.duoq.coursehub.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse_IdOrderByOrderIndex(Long courseId);
}
