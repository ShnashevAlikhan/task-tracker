package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.mapper.UserMapper;
import com.temerlan.task_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
