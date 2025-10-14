package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherLessonMapper {

    @Mapping(target = "curriculumLesson", ignore = true)
    TeacherLesson toEntity(TeacherLessonRequest request);

    @Mapping(target = "curriculumLessonId", source = "curriculumLesson.id")
    @Mapping(target = "curriculumLessonTitle", source = "curriculumLesson.title")
    @Mapping(target = "teacherName", ignore = true)
    @Mapping(target = "className", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "fileCount", expression = "java(lesson.getLessonFiles() != null ? lesson.getLessonFiles().size() : 0)")
    TeacherLessonResponse toResponse(TeacherLesson lesson);

    @Mapping(target = "curriculumLesson", ignore = true)
    @Mapping(target = "teacherId", ignore = true)
    void updateEntity(@MappingTarget TeacherLesson lesson, TeacherLessonRequest request);
}