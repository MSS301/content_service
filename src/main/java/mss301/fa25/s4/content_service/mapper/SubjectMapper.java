package mss301.fa25.s4.content_service.mapper;

import mss301.fa25.s4.content_service.dto.request.SubjectRequest;
import mss301.fa25.s4.content_service.dto.response.SubjectResponse;
import mss301.fa25.s4.content_service.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {

    Subject toEntity(SubjectRequest request);

    @Mapping(target = "chapterCount", expression = "java(subject.getChapters() != null ? subject.getChapters().size() : 0)")
    SubjectResponse toResponse(Subject subject);

    void updateEntity(@MappingTarget Subject subject, SubjectRequest request);
}