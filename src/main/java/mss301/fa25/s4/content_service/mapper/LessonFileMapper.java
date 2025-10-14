package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;
import mss301.fa25.s4.content_service.entity.LessonFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonFileMapper {

    @Mapping(target = "lesson", ignore = true)
    LessonFile toEntity(LessonFileRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
    @Mapping(target = "uploaderName", ignore = true)
    LessonFileResponse toResponse(LessonFile file);
}