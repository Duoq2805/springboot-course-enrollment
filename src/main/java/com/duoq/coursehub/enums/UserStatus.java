package com.duoq.coursehub.enums;

import com.duoq.coursehub.exception.BadRequestException;

public enum UserStatus {
    PENDING_VERIFICATION("Pending Verification"),
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    DELETED("Deleted");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static UserStatus fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new BadRequestException("User status cannot be null or blank");
        }
        String normalized = input.trim();
        try {
            // Try enum name first (ACTIVE, PENDING_VERIFICATION, etc.)
            return UserStatus.valueOf(normalized.toUpperCase().replace(" ", "_"));
        } catch (BadRequestException ex) {
            // Try matching by value (Active, Pending Verification, etc.)
            for (UserStatus status : values()) {
                if (status.value.equalsIgnoreCase(normalized)) {
                    return status;
                }
            }
            throw new BadRequestException("Unsupported user status: " + input);
        }
    }
}
