package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;

import java.util.List;

public interface CurriculumLessonService {
    CurriculumLessonResponse createLesson(CurriculumLessonRequest request);

    CurriculumLessonResponse getLessonById(Integer id);

    List<CurriculumLessonResponse> getAllLessons();

    List<CurriculumLessonResponse> getLessonsByChapter(Integer chapterId);

    CurriculumLessonResponse updateLesson(Integer id, CurriculumLessonRequest request);

    void deleteLesson(Integer id);
}