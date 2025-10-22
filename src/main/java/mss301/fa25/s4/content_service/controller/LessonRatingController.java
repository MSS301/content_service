package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.service.LessonRatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson-ratings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Lesson Rating Management", description = "APIs for managing lesson ratings")
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
    @Operation(summary = "Get lesson ratings", description = "Retrieve paginated list of lesson ratings with optional filtering")
    public PaginatedResponse<LessonRatingResponse> getRatings(
            @Parameter(description = "Filter by lesson ID") @RequestParam(required = false) Integer lessonId,
            @Parameter(description = "Filter by student ID") @RequestParam(required = false) Integer studentId,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get ratings with pagination");

        Page<LessonRatingResponse> ratings;
        if (lessonId != null) {
            ratings = ratingService.getRatingsByLesson(lessonId, pageable);
        } else if (studentId != null) {
            ratings = ratingService.getRatingsByStudent(studentId, pageable);
        } else {
            return PaginatedResponse.<LessonRatingResponse>builder()
                    .message("Please provide lessonId or studentId parameter")
                    .build();
        }

        return PaginatedResponse.of(ratings);
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