package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.lesson.LessonCreateRequest;
import com.duoq.coursehub.dto.lesson.LessonResponse;
import com.duoq.coursehub.exception.NotFoundException;
import com.duoq.coursehub.model.Course;
import com.duoq.coursehub.model.Lesson;
import com.duoq.coursehub.repository.CourseRepo;
import com.duoq.coursehub.repository.LessonRepo;
import com.duoq.coursehub.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepo lessonRepo;
    private final CourseRepo courseRepo;

    @Override
    public LessonResponse create(LessonCreateRequest request) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setOrderIndex(request.getOrderIndex());
        lesson.setCourse(getCourseOrThrow(request.getCourseId()));
        return toResponse(lessonRepo.save(lesson));
    }

    @Override
    public List<LessonResponse> getByCourse(Long courseId) {
        return lessonRepo.findByCourse_IdOrderByOrderIndex(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    // ================= HELPER METHODS =================

    private LessonResponse toResponse(Lesson l) {
        LessonResponse res = new LessonResponse();
        res.setId(l.getId());
        res.setTitle(l.getTitle());
        res.setContent(l.getContent());
        res.setOrderIndex(l.getOrderIndex());
        res.setCourseId(l.getCourse().getId());
        return res;
    }

    private Course getCourseOrThrow(Long courseId) {
        return courseRepo.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
    }

}
