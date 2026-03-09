package com.duoq.coursehub.enums;

import com.duoq.coursehub.exception.BadRequestException;

public enum CourseStatus {
    DRAFT("Draft"),
    PUBLISHED("Published"),
    ARCHIVED("Archived"),
    DELETED("Deleted");

    private final String value;

    CourseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CourseStatus fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new BadRequestException("Course status cannot be null or blank");
        }
        String normalized = input.trim();
        try {
            return CourseStatus.valueOf(normalized.toUpperCase());
        } catch (BadRequestException ex) {
            for (CourseStatus status : values()) {
                if (status.value.equalsIgnoreCase(normalized)) {
                    return status;
                }
            }
            throw new BadRequestException("Unsupported course status: " + input);
        }
    }
}
