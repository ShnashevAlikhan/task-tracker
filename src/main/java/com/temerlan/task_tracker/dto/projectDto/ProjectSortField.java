package com.temerlan.task_tracker.dto.projectDto;

public enum ProjectSortField {
    TITLE("title"),
    CREATED_AT("createdAt"),
    UPDATE_AT("updateAt");

    private String property;

        ProjectSortField(String property) {
            this.property = property;
        }

    public String getProperty() {
        return property;
    }
}
