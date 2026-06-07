package com.temerlan.task_tracker.mapper;

import com.temerlan.task_tracker.dto.taskDto.TaskResponse;
import com.temerlan.task_tracker.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toResponse(Task task);
}
