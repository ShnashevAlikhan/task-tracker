package com.temerlan.task_tracker.dto.projectDto;

public enum ProjectSortField {
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String property;

        ProjectSortField(String property) {
            this.property = property;
        }

    public String getProperty() {
        return property;
    }
}
