package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse body = service.userCreate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);

    }
}
