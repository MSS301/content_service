package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.constant.GradeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChapterService {
    ChapterResponse createChapter(ChapterRequest request);

    ChapterResponse getChapterById(Integer id);

//    List<ChapterResponse> getAllChapters();
    Page<ChapterResponse> getAllChapters(Pageable pageable);

//    List<ChapterResponse> getChaptersBySubject(Integer subjectId);
    Page<ChapterResponse> getChaptersBySubject(Integer subjectId, Pageable pageable);

//    List<ChapterResponse> getChaptersByGrade(GradeLevel grade);
    Page<ChapterResponse> getChaptersByGrade(GradeLevel grade, Pageable pageable);

//    List<ChapterResponse> getChaptersBySubjectAndGrade(Integer subjectId, GradeLevel grade);
    Page<ChapterResponse> getChaptersBySubjectAndGrade(Integer subjectId, GradeLevel grade, Pageable pageable);

    ChapterResponse updateChapter(Integer id, ChapterRequest request);

    void deleteChapter(Integer id);
}