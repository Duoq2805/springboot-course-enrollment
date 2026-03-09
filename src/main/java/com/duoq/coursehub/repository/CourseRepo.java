package com.duoq.coursehub.repository;

import com.duoq.coursehub.enums.CourseStatus;
import com.duoq.coursehub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findByStatusNot(CourseStatus status);

}
