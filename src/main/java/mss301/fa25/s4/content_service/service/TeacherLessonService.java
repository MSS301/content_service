package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.SelfTeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonUpdateRequest;
import mss301.fa25.s4.content_service.dto.response.LessonAnalyticsResponse;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherLessonService {
    TeacherLessonResponse createLesson(TeacherLessonRequest request);
    
    TeacherLessonResponse createSelfLesson(SelfTeacherLessonRequest request, Integer teacherId);
    
    Page<TeacherLessonResponse> getMyLessons(Integer teacherId, Pageable pageable);

    TeacherLessonResponse getLessonById(Integer id);

//    List<TeacherLessonResponse> getAllLessons();
    Page<TeacherLessonResponse> getAllLessons(Pageable pageable);

//    List<TeacherLessonResponse> getLessonsByTeacher(Integer teacherId);
    Page<TeacherLessonResponse> getLessonsByTeacher(Integer teacherId, Pageable pageable);

//    List<TeacherLessonResponse> getLessonsByStatus(TeacherLessonStatus status);
    Page<TeacherLessonResponse> getLessonsByStatus(TeacherLessonStatus status, Pageable pageable);

//    List<TeacherLessonResponse> getLessonsByClass(Integer classId);
    Page<TeacherLessonResponse> getLessonsByClass(Integer classId, Pageable pageable);
    
    Page<TeacherLessonResponse> getPublishedLessonsByClass(Integer classId, Pageable pageable);

    TeacherLessonResponse updateLesson(Integer id, TeacherLessonUpdateRequest request);

    void deleteLesson(Integer id);

    void incrementViewCount(Integer id);

    LessonAnalyticsResponse getLessonAnalytics(Integer id);
}