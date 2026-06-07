package com.temerlan.task_tracker.dto.taskDto;


public enum TaskSortField {
    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    STATUS("status"),
    PRIORITY("priority"),
    DEADLINE("deadline");

    private final String property;

    TaskSortField(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
