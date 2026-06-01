package com.temerlan.task_tracker.mapper;


import com.temerlan.task_tracker.dto.ProjectResponse;
import com.temerlan.task_tracker.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    public ProjectResponse toResponse(Project project);
}
