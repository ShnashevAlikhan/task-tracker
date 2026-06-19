package com.temerlan.task_tracker.exception;

import com.temerlan.task_tracker.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handlerBadRequestException(
            Exception ex,
            HttpServletRequest request)
    {
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
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
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
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
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
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
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
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
        ApiError apiError = ApiError.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handlerEmailAlreadyExistsException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = ApiError.of(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiError> handlerCommentNotFoundException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = ApiError.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }
}
