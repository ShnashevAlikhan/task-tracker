package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.ProjectResponse;
import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.entity.Project;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.exception.ProjectNotFoundException;
import com.temerlan.task_tracker.exception.UserNotFoundException;
import com.temerlan.task_tracker.mapper.ProjectMapper;
import com.temerlan.task_tracker.mapper.UserMapper;
import com.temerlan.task_tracker.repository.ProjectRepository;
import com.temerlan.task_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService {

    private UserRepository repository;
    private ProjectRepository projectRepository;
    private UserMapper mapper;
    private ProjectMapper projectMapper;
    private PasswordEncoder passwordEncoder;
    private CurrentUserService currentUserService;

    @Autowired
    public UserService(UserRepository repository, ProjectRepository projectRepository, UserMapper mapper, ProjectMapper projectMapper, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
        this.projectMapper = projectMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    public UserResponse userCreate(UserRequest request) {
        UserRequest encodedRequest = new UserRequest(
                request.email(),
                request.name(),
                passwordEncoder.encode(request.password())
        );
        User user = User.create(
                request.email(),
                request.name(),
                encodedRequest.password()
        );

        repository.save(user);

        log.info("Created user with username: {} by id: {}", user.getEmail(), user.getId());
        return mapper.toUserResponse(user);
    }
}
