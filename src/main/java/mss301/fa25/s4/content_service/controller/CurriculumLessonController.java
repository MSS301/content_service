package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;
import mss301.fa25.s4.content_service.service.CurriculumLessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curriculum-lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
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
    public ApiResponse<List<CurriculumLessonResponse>> getAllLessons(
            @RequestParam(required = false) Integer chapterId) {
        log.info("REST request to get all curriculum lessons");

        if (chapterId != null) {
            return ApiResponse.<List<CurriculumLessonResponse>>builder()
                    .result(lessonService.getLessonsByChapter(chapterId))
                    .build();
        }

        return ApiResponse.<List<CurriculumLessonResponse>>builder()
                .result(lessonService.getAllLessons())
                .build();
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