package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public User getCurrentUser() {
        String email = getCurrentUserEmail();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}