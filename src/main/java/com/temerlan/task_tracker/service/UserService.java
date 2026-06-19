package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.userDto.UserRequest;
import com.temerlan.task_tracker.dto.userDto.UserResponse;
import com.temerlan.task_tracker.dto.userDto.UserUpdate;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.exception.BadRequestException;
import com.temerlan.task_tracker.exception.EmailAlreadyExistsException;
import com.temerlan.task_tracker.exception.UserNotFoundException;
import com.temerlan.task_tracker.mapper.UserMapper;
import com.temerlan.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;


    public UserResponse userCreate(UserRequest request) {
        if (repository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new BadRequestException("User with email = " + request.email() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.create(
                request.email(),
                request.name(),
                encodedPassword
        );

        repository.save(user);

        log.info("Created user with username: {} by id: {}", user.getEmail(), user.getId());
        return mapper.toUserResponse(user);
    }

    public boolean userUpdate(UserUpdate update) {
        Long userId = currentUserService.getCurrentUserId();

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: + " + userId + " not found"));

        boolean changedEmail = update.email() != null &&
                !update.email().equalsIgnoreCase(user.getEmail());

        if(changedEmail) {
            repository.findByEmailIgnoreCase(update.email())
                    .filter(existingUser ->
                            !existingUser.getId().equals(userId))
                    .ifPresent(existingUser -> {
                        throw new EmailAlreadyExistsException("Email is busy");
                            });
        }

        String encodedPassword = update.password() == null
                ? null : passwordEncoder.encode(update.password());

        user.update(
                update.email(),
                update.name(),
                encodedPassword
        );

        log.info("User with id {} updated information", userId);

        return changedEmail;
    }
}
