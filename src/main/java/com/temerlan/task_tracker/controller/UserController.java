package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.service.CurrentUserService;
import com.temerlan.task_tracker.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final CurrentUserService currentUserService;

    @Autowired
    public UserController(UserService service, CurrentUserService currentUserService) {
        this.service = service;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse body = service.userCreate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);

    }

    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        User user = currentUserService.getCurrentUser();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
