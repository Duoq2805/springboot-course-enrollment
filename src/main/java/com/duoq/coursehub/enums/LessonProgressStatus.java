package com.duoq.coursehub.enums;

import com.duoq.coursehub.exception.BadRequestException;

public enum LessonProgressStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String value;

    LessonProgressStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static LessonProgressStatus fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new BadRequestException("Lesson progress status cannot be null or blank");
        }
        String normalized = input.trim();
        try {
            return LessonProgressStatus.valueOf(normalized.toUpperCase().replace(" ", "_"));
        } catch (BadRequestException ex) {
            for (LessonProgressStatus status : values()) {
                if (status.value.equalsIgnoreCase(normalized)) {
                    return status;
                }
            }
            throw new BadRequestException("Unsupported lesson progress status: " + input);
        }
    }
}
