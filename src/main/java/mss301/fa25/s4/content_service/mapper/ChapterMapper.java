package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChapterMapper {

    @Mapping(target = "subject", ignore = true)
    Chapter toEntity(ChapterRequest request);

    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "subjectName", source = "subject.name")
    @Mapping(target = "lessonCount", expression = "java(chapter.getCurriculumLessons() != null ? chapter.getCurriculumLessons().size() : 0)")
    ChapterResponse toResponse(Chapter chapter);

    @Mapping(target = "subject", ignore = true)
    void updateEntity(@MappingTarget Chapter chapter, ChapterRequest request);
}