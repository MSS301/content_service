package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonUpdateRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonAnalyticsResponse;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.enums.TeacherLessonStatus;
import mss301.fa25.s4.content_service.service.TeacherLessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher-lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TeacherLessonController {
    TeacherLessonService lessonService;

    @PostMapping
    public ApiResponse<TeacherLessonResponse> createLesson(@Valid @RequestBody TeacherLessonRequest request) {
        log.info("REST request to create teacher lesson");
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.createLesson(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherLessonResponse> getLessonById(@PathVariable Integer id) {
        log.info("REST request to get teacher lesson by id: {}", id);
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.getLessonById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TeacherLessonResponse>> getAllLessons(
            @RequestParam(required = false) Integer teacherId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer classId) {
        log.info("REST request to get all teacher lessons");

        if (teacherId != null) {
            return ApiResponse.<List<TeacherLessonResponse>>builder()
                    .result(lessonService.getLessonsByTeacher(teacherId))
                    .build();
        } else if (status != null) {
            TeacherLessonStatus lessonStatus = TeacherLessonStatus.valueOf(status);
            return ApiResponse.<List<TeacherLessonResponse>>builder()
                    .result(lessonService.getLessonsByStatus(lessonStatus))
                    .build();
        } else if (classId != null) {
            return ApiResponse.<List<TeacherLessonResponse>>builder()
                    .result(lessonService.getLessonsByClass(classId))
                    .build();
        }

        return ApiResponse.<List<TeacherLessonResponse>>builder()
                .result(lessonService.getAllLessons())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TeacherLessonResponse> updateLesson(
            @PathVariable Integer id,
            @Valid @RequestBody TeacherLessonUpdateRequest request) {
        log.info("REST request to update teacher lesson id: {}", id);
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.updateLesson(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Integer id) {
        log.info("REST request to delete teacher lesson id: {}", id);
        lessonService.deleteLesson(id);
        return ApiResponse.<Void>builder()
                .message("Teacher lesson deleted successfully")
                .build();
    }

    @PostMapping("/{id}/view")
    public ApiResponse<Void> incrementViewCount(@PathVariable Integer id) {
        log.info("REST request to increment view count for lesson id: {}", id);
        lessonService.incrementViewCount(id);
        return ApiResponse.<Void>builder()
                .message("View count incremented")
                .build();
    }

    @GetMapping("/{id}/analytics")
    public ApiResponse<LessonAnalyticsResponse> getLessonAnalytics(@PathVariable Integer id) {
        log.info("REST request to get analytics for lesson id: {}", id);
        return ApiResponse.<LessonAnalyticsResponse>builder()
                .result(lessonService.getLessonAnalytics(id))
                .build();
    }
}