package com.duoq.coursehub.repository;

import com.duoq.coursehub.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonProgressRepo extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByUser_IdAndLesson_Id(Long userId, Long lessonId);

    List<LessonProgress> findByUser_Id(Long userId);
}
