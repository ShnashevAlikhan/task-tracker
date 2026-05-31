package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.mapper.UserMapper;
import com.temerlan.task_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository repository;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;

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
        User user = repository.save(mapper.toUser(encodedRequest));

        return mapper.toUserResponse(user);
    }
}
