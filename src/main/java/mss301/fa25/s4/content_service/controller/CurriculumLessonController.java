package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.service.CurriculumLessonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curriculum-lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Curriculum Lesson Management", description = "APIs for managing curriculum lessons")
public class CurriculumLessonController {
    CurriculumLessonService lessonService;

    @PostMapping
    public ApiResponse<CurriculumLessonResponse> createLesson(@Valid @RequestBody CurriculumLessonRequest request) {
        log.info("REST request to create curriculum lesson");
        return ApiResponse.<CurriculumLessonResponse>builder()
                .result(lessonService.createLesson(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CurriculumLessonResponse> getLessonById(@PathVariable Integer id) {
        log.info("REST request to get curriculum lesson by id: {}", id);
        return ApiResponse.<CurriculumLessonResponse>builder()
                .result(lessonService.getLessonById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all curriculum lessons", description = "Retrieve paginated list of curriculum lessons with optional filtering by chapter")
    public PaginatedResponse<CurriculumLessonResponse> getAllLessons(
            @Parameter(description = "Filter by chapter ID") @RequestParam(required = false) Integer chapterId,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "orderIndex") Pageable pageable) {
        log.info("REST request to get all curriculum lessons with pagination");

        Page<CurriculumLessonResponse> lessons;
        if (chapterId != null) {
            lessons = lessonService.getLessonsByChapter(chapterId, pageable);
        } else {
            lessons = lessonService.getAllLessons(pageable);
        }

        return PaginatedResponse.of(lessons);
    }

    @PutMapping("/{id}")
    public ApiResponse<CurriculumLessonResponse> updateLesson(
            @PathVariable Integer id,
            @Valid @RequestBody CurriculumLessonRequest request) {
        log.info("REST request to update curriculum lesson id: {}", id);
        return ApiResponse.<CurriculumLessonResponse>builder()
                .result(lessonService.updateLesson(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Integer id) {
        log.info("REST request to delete curriculum lesson id: {}", id);
        lessonService.deleteLesson(id);
        return ApiResponse.<Void>builder()
                .message("Curriculum lesson deleted successfully")
                .build();
    }
}