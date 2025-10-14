package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;
import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CurriculumLessonMapper {

    @Mapping(target = "chapter", ignore = true)
    CurriculumLesson toEntity(CurriculumLessonRequest request);

    @Mapping(target = "chapterId", source = "chapter.id")
    @Mapping(target = "chapterTitle", source = "chapter.title")
    CurriculumLessonResponse toResponse(CurriculumLesson lesson);

    @Mapping(target = "chapter", ignore = true)
    void updateEntity(@MappingTarget CurriculumLesson lesson, CurriculumLessonRequest request);
}