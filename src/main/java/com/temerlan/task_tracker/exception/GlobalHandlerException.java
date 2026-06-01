package com.temerlan.task_tracker.exception;

import com.temerlan.task_tracker.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handlerBadRequestException(
            Exception ex,
            HttpServletRequest request)
    {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiError> handlerProjectNotFound(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handlerUserNotFound(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handlerTaskNotFound(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handlerIllegalArgumentException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

}
