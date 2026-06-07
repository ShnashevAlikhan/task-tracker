package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.userDto.UserRequest;
import com.temerlan.task_tracker.dto.userDto.UserResponse;
import com.temerlan.task_tracker.dto.userDto.UserUpdate;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.service.CurrentUserService;
import com.temerlan.task_tracker.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @PatchMapping("/me")
    ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdate update,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        boolean changedEmail = service.userUpdate(update);

        if(changedEmail) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity
                .ok()
                .build();
    }
}
