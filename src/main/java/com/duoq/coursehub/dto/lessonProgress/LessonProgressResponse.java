package com.duoq.coursehub.dto.lessonProgress;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LessonProgressResponse {
    private Long id;
    private Long userId;
    private Long lessonId;
    private Boolean completed;
    private LocalDateTime completedAt; // THÊM DÒNG NÀY
}