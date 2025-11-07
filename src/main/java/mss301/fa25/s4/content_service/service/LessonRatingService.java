package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.request.SelfLessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonRatingService {
    LessonRatingResponse rateLesson(LessonRatingRequest request);
    
    LessonRatingResponse rateLessonSelf(SelfLessonRatingRequest request, Integer studentId);
    
    Page<LessonRatingResponse> getMyRatings(Integer studentId, Pageable pageable);

    LessonRatingResponse getRatingById(Integer id);

//    List<LessonRatingResponse> getRatingsByLesson(Integer lessonId);
    Page<LessonRatingResponse> getRatingsByLesson(Integer lessonId, Pageable pageable);

//    List<LessonRatingResponse> getRatingsByStudent(Integer studentId);
    Page<LessonRatingResponse> getRatingsByStudent(Integer studentId, Pageable pageable);

    LessonRatingResponse updateRating(Integer id, LessonRatingRequest request);

    void deleteRating(Integer id);
}