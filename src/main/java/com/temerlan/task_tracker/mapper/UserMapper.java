package com.temerlan.task_tracker.mapper;

import com.temerlan.task_tracker.dto.UserRequest;
import com.temerlan.task_tracker.dto.UserResponse;
import com.temerlan.task_tracker.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
}
