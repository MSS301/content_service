package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;
import mss301.fa25.s4.content_service.entity.LessonRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonRatingMapper {

    @Mapping(target = "lesson", ignore = true)
    LessonRating toEntity(LessonRatingRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
    @Mapping(target = "studentName", ignore = true)
    LessonRatingResponse toResponse(LessonRating rating);
}