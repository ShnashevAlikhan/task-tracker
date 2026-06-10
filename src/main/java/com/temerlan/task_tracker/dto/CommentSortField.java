package com.temerlan.task_tracker.dto;

public enum CommentSortField {
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String property;

    CommentSortField(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
