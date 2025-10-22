package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurriculumLessonService {
    CurriculumLessonResponse createLesson(CurriculumLessonRequest request);

    CurriculumLessonResponse getLessonById(Integer id);

//    List<CurriculumLessonResponse> getAllLessons();
    Page<CurriculumLessonResponse> getAllLessons(Pageable pageable);

//    List<CurriculumLessonResponse> getLessonsByChapter(Integer chapterId);
    Page<CurriculumLessonResponse> getLessonsByChapter(Integer chapterId, Pageable pageable);

    CurriculumLessonResponse updateLesson(Integer id, CurriculumLessonRequest request);

    void deleteLesson(Integer id);
}