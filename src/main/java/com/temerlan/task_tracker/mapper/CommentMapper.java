package com.temerlan.task_tracker.mapper;


import com.temerlan.task_tracker.dto.CommentResponse;
import com.temerlan.task_tracker.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponse toResponse(Comment comment);
}
