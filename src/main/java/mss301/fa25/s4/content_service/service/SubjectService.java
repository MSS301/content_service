package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.SubjectRequest;
import mss301.fa25.s4.content_service.dto.response.SubjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    SubjectResponse createSubject(SubjectRequest request);

    SubjectResponse getSubjectById(Integer id);

//    List<SubjectResponse> getAllSubjects();
    Page<SubjectResponse> getAllSubjects(Pageable pageable);

//    List<SubjectResponse> searchSubjectsByName(String name);
    Page<SubjectResponse> searchSubjectsByName(String name, Pageable pageable);

    SubjectResponse updateSubject(Integer id, SubjectRequest request);

    void deleteSubject(Integer id);
}