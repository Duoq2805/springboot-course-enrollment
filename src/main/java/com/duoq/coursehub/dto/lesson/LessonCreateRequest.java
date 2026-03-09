package com.duoq.coursehub.dto.lesson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonCreateRequest {
    private String title;
    private String content;
    private Integer orderIndex;
    private Long courseId;
}
