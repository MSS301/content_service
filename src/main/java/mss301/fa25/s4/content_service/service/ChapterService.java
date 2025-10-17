package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.constant.GradeLevel;

import java.util.List;

public interface ChapterService {
    ChapterResponse createChapter(ChapterRequest request);

    ChapterResponse getChapterById(Integer id);

    List<ChapterResponse> getAllChapters();

    List<ChapterResponse> getChaptersBySubject(Integer subjectId);

    List<ChapterResponse> getChaptersByGrade(GradeLevel grade);

    List<ChapterResponse> getChaptersBySubjectAndGrade(Integer subjectId, GradeLevel grade);

    ChapterResponse updateChapter(Integer id, ChapterRequest request);

    void deleteChapter(Integer id);
}