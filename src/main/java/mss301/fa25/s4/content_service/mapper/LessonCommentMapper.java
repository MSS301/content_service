package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;
import mss301.fa25.s4.content_service.entity.LessonComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonCommentMapper {

    @Mapping(target = "lesson", ignore = true)
    LessonComment toEntity(LessonCommentRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
    @Mapping(target = "studentName", ignore = true)
    LessonCommentResponse toResponse(LessonComment comment);

    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "studentId", ignore = true)
    void updateEntity(@MappingTarget LessonComment comment, LessonCommentRequest request);
}