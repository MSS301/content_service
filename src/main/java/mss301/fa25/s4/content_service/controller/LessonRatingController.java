package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;
import mss301.fa25.s4.content_service.service.LessonRatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson-ratings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonRatingController {
    LessonRatingService ratingService;

    @PostMapping
    public ApiResponse<LessonRatingResponse> rateLesson(@Valid @RequestBody LessonRatingRequest request) {
        log.info("REST request to rate lesson");
        return ApiResponse.<LessonRatingResponse>builder()
                .result(ratingService.rateLesson(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonRatingResponse> getRatingById(@PathVariable Integer id) {
        log.info("REST request to get rating by id: {}", id);
        return ApiResponse.<LessonRatingResponse>builder()
                .result(ratingService.getRatingById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<LessonRatingResponse>> getRatings(
            @RequestParam(required = false) Integer lessonId,
            @RequestParam(required = false) Integer studentId) {
        log.info("REST request to get ratings");

        if (lessonId != null) {
            return ApiResponse.<List<LessonRatingResponse>>builder()
                    .result(ratingService.getRatingsByLesson(lessonId))
                    .build();
        } else if (studentId != null) {
            return ApiResponse.<List<LessonRatingResponse>>builder()
                    .result(ratingService.getRatingsByStudent(studentId))
                    .build();
        }

        return ApiResponse.<List<LessonRatingResponse>>builder()
                .message("Please provide lessonId or studentId parameter")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LessonRatingResponse> updateRating(
            @PathVariable Integer id,
            @Valid @RequestBody LessonRatingRequest request) {
        log.info("REST request to update rating id: {}", id);
        return ApiResponse.<LessonRatingResponse>builder()
                .result(ratingService.updateRating(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRating(@PathVariable Integer id) {
        log.info("REST request to delete rating id: {}", id);
        ratingService.deleteRating(id);
        return ApiResponse.<Void>builder()
                .message("Rating deleted successfully")
                .build();
    }
}