package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;

import java.util.List;

public interface LessonRatingService {
    LessonRatingResponse rateLesson(LessonRatingRequest request);

    LessonRatingResponse getRatingById(Integer id);

    List<LessonRatingResponse> getRatingsByLesson(Integer lessonId);

    List<LessonRatingResponse> getRatingsByStudent(Integer studentId);

    LessonRatingResponse updateRating(Integer id, LessonRatingRequest request);

    void deleteRating(Integer id);
}