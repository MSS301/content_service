package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonCommentService {
    LessonCommentResponse createComment(LessonCommentRequest request);

    LessonCommentResponse getCommentById(Integer id);

//    List<LessonCommentResponse> getCommentsByLesson(Integer lessonId);
    Page<LessonCommentResponse> getCommentsByLesson(Integer lessonId, Pageable pageable);

//    List<LessonCommentResponse> getCommentsByStudent(Integer studentId);
    Page<LessonCommentResponse> getCommentsByStudent(Integer studentId, Pageable pageable);

    LessonCommentResponse updateComment(Integer id, LessonCommentRequest request);

    void deleteComment(Integer id);
}