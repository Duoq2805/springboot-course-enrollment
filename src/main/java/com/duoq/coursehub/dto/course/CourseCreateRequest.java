    package com.duoq.coursehub.dto.course;

    import lombok.Getter;
    import lombok.Setter;

    import java.math.BigDecimal;

    @Getter
    @Setter
    public class CourseCreateRequest {
        private String title;
        private String description;
        private BigDecimal price;
        private Long instructorId;
    }
