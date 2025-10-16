package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;

import java.util.List;

public interface LessonCommentService {
    LessonCommentResponse createComment(LessonCommentRequest request);

    LessonCommentResponse getCommentById(Integer id);

    List<LessonCommentResponse> getCommentsByLesson(Integer lessonId);

    List<LessonCommentResponse> getCommentsByStudent(Integer studentId);

    LessonCommentResponse updateComment(Integer id, LessonCommentRequest request);

    void deleteComment(Integer id);
}