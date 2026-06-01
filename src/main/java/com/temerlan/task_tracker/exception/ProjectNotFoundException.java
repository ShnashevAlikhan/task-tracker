package com.temerlan.task_tracker.exception;

public class ProjectNotFoundException extends RuntimeException{

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
