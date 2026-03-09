package com.duoq.coursehub.service.impl;

import com.duoq.coursehub.dto.course.CourseCreateRequest;
import com.duoq.coursehub.dto.course.CourseResponse;
import com.duoq.coursehub.dto.course.CourseUpdateRequest;
import com.duoq.coursehub.enums.CourseStatus;
import com.duoq.coursehub.enums.Role;
import com.duoq.coursehub.exception.BadRequestException;
import com.duoq.coursehub.exception.ConflictException;
import com.duoq.coursehub.exception.NotFoundException;
import com.duoq.coursehub.model.Course;
import com.duoq.coursehub.model.User;
import com.duoq.coursehub.repository.CourseRepo;
import com.duoq.coursehub.repository.UserRepo;
import com.duoq.coursehub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    @Override
    public CourseResponse createCourse(CourseCreateRequest request) {
        User instructor = getInstructorOrThrow(request.getInstructorId());

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(CourseStatus.DRAFT);
        course.setPrice(request.getPrice());
        course.setInstructor(instructor);

        return mapToResponse(courseRepo.save(course));
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepo.findByStatusNot(CourseStatus.DELETED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return mapToResponse(getCourseOrThrow(id));
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course course = getCourseOrThrow(id);

        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            course.setStatus(CourseStatus.fromString(request.getStatus()));
        }

        return mapToResponse(courseRepo.save(course));
    }

    @Override
    public CourseResponse deleteCourse(Long id) {
        Course course = getCourseOrThrow(id);

        if (course.getStatus() == CourseStatus.PUBLISHED) {
            throw new ConflictException("Cannot delete published course");
        }

        course.setStatus(CourseStatus.DELETED);
        return mapToResponse(courseRepo.save(course));
    }


    // ================= HELPER METHODS =================
    private Course getCourseOrThrow(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found"));
    }

    private User getInstructorOrThrow(Long instructorId) {
        if (instructorId == null) {
            throw new BadRequestException("instructorId is required");
        }
        User user = userRepo.findById(instructorId)
                .orElseThrow(() ->
                        new NotFoundException("Instructor not found with id: " + instructorId));

        if (user.getRole() != Role.INSTRUCTOR) {
            throw new BadRequestException("User is not an instructor");
        }

        return user;
    }

    private CourseResponse mapToResponse(Course course) {
        CourseResponse res = new CourseResponse();
        res.setId(course.getId());
        res.setTitle(course.getTitle());
        res.setDescription(course.getDescription());
        res.setStatus(course.getStatus().name());
        res.setPrice(course.getPrice());
        res.setInstructorId(course.getInstructor().getId());
        res.setCreatedAt(course.getCreatedAt());
        return res;
    }
}
