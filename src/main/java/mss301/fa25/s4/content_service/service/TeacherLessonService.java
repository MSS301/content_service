package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonUpdateRequest;
import mss301.fa25.s4.content_service.dto.response.LessonAnalyticsResponse;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;

import java.util.List;

public interface TeacherLessonService {
    TeacherLessonResponse createLesson(TeacherLessonRequest request);

    TeacherLessonResponse getLessonById(Integer id);

    List<TeacherLessonResponse> getAllLessons();

    List<TeacherLessonResponse> getLessonsByTeacher(Integer teacherId);

    List<TeacherLessonResponse> getLessonsByStatus(TeacherLessonStatus status);

    List<TeacherLessonResponse> getLessonsByClass(Integer classId);

    TeacherLessonResponse updateLesson(Integer id, TeacherLessonUpdateRequest request);

    void deleteLesson(Integer id);

    void incrementViewCount(Integer id);

    LessonAnalyticsResponse getLessonAnalytics(Integer id);
}