package com.duoq.coursehub.enums;

import com.duoq.coursehub.exception.BadRequestException;

public enum Role {
    ADMIN("Admin"),
    INSTRUCTOR("Instructor"),
    STUDENT("Student");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Role fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new BadRequestException("Role cannot be null or blank");
        }
        String normalized = input.trim();
        try {
            return Role.valueOf(normalized.toUpperCase());
        } catch (BadRequestException ex) {
            for (Role role : values()) {
                if (role.value.equalsIgnoreCase(normalized)) {
                    return role;
                }
            }
            throw new BadRequestException("Unsupported role: " + input);
        }
    }
}
