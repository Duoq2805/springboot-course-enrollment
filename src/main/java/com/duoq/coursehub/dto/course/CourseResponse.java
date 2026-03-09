package com.duoq.coursehub.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private BigDecimal price;
    private Long instructorId;
    private LocalDateTime createdAt;

}
