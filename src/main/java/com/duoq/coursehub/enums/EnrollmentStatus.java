package com.duoq.coursehub.enums;

import com.duoq.coursehub.exception.BadRequestException;

public enum EnrollmentStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String value;

    EnrollmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static EnrollmentStatus fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new BadRequestException("Enrollment status is required");
        }

        String normalized = input.trim();

        for (EnrollmentStatus status : values()) {
            if (status.name().equalsIgnoreCase(normalized)
                    || status.value.equalsIgnoreCase(normalized)) {
                return status;
            }
        }

        throw new BadRequestException("Unsupported enrollment status: " + input);
    }

}
