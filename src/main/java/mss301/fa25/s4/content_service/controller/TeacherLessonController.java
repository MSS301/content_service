package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.SelfTeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonUpdateRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonAnalyticsResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;
import mss301.fa25.s4.content_service.service.TeacherLessonService;
import mss301.fa25.s4.content_service.service.UserProfileClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher-lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Teacher Lesson Management", description = "APIs for managing teacher lessons")
public class TeacherLessonController {
    TeacherLessonService lessonService;
    UserProfileClientService userProfileClientService;

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ApiResponse<TeacherLessonResponse> createLesson(@Valid @RequestBody TeacherLessonRequest request) {
        log.info("REST request to create teacher lesson");
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.createLesson(request))
                .build();
    }

    @PostMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<TeacherLessonResponse> createSelfLesson(@Valid @RequestBody SelfTeacherLessonRequest request) {
        log.info("REST request to create self teacher lesson");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountId = authentication.getName(); // UUID from JWT token
        
        // Get teacherId (Integer) from auth-service using accountId (UUID)
        Integer teacherId = userProfileClientService.getUserProfileId(accountId);
        
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.createSelfLesson(request, teacherId))
                .build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public PaginatedResponse<TeacherLessonResponse> getMyLessons(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get my teacher lessons");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountId = authentication.getName(); // UUID from JWT token
        
        // Get teacherId (Integer) from auth-service using accountId (UUID)
        Integer teacherId = userProfileClientService.getUserProfileId(accountId);
        
        Page<TeacherLessonResponse> lessons = lessonService.getMyLessons(teacherId, pageable);
        return PaginatedResponse.of(lessons);
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherLessonResponse> getLessonById(@PathVariable Integer id) {
        log.info("REST request to get teacher lesson by id: {}", id);
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.getLessonById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all teacher lessons", description = "Retrieve paginated list of teacher lessons with optional filtering")
    public PaginatedResponse<TeacherLessonResponse> getAllLessons(
            @Parameter(description = "Filter by teacher ID") @RequestParam(required = false) Integer teacherId,
            @Parameter(description = "Filter by lesson status") @RequestParam(required = false) String status,
            @Parameter(description = "Filter by class ID") @RequestParam(required = false) Integer classId,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get all teacher lessons with pagination");

        Page<TeacherLessonResponse> lessons;
        if (teacherId != null) {
            lessons = lessonService.getLessonsByTeacher(teacherId, pageable);
        } else if (status != null) {
            TeacherLessonStatus lessonStatus = TeacherLessonStatus.valueOf(status);
            lessons = lessonService.getLessonsByStatus(lessonStatus, pageable);
        } else if (classId != null) {
            lessons = lessonService.getLessonsByClass(classId, pageable);
        } else {
            lessons = lessonService.getAllLessons(pageable);
        }

        return PaginatedResponse.of(lessons);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ApiResponse<TeacherLessonResponse> updateLesson(
            @PathVariable Integer id,
            @Valid @RequestBody TeacherLessonUpdateRequest request) {
        log.info("REST request to update teacher lesson id: {}", id);
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.updateLesson(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteLesson(@PathVariable Integer id) {
        log.info("REST request to delete teacher lesson id: {}", id);
        lessonService.deleteLesson(id);
        return ApiResponse.<Void>builder()
                .message("Teacher lesson deleted successfully")
                .build();
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get lessons by class for students", description = "Get all published lessons for a specific class")
    @PreAuthorize("isAuthenticated()")
    public PaginatedResponse<TeacherLessonResponse> getLessonsByClassForStudents(
            @PathVariable Integer classId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get published lessons for class ID: {}", classId);
        Page<TeacherLessonResponse> lessons = lessonService.getPublishedLessonsByClass(classId, pageable);
        return PaginatedResponse.of(lessons);
    }

    @PostMapping("/{id}/view")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> incrementViewCount(@PathVariable Integer id) {
        log.info("REST request to increment view count for lesson id: {}", id);
        lessonService.incrementViewCount(id);
        return ApiResponse.<Void>builder()
                .message("View count incremented")
                .build();
    }

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @Operation(summary = "Publish a draft lesson", description = "Change lesson status from DRAFT to PUBLISHED")
    public ApiResponse<TeacherLessonResponse> publishLesson(@PathVariable Integer id) {
        log.info("REST request to publish lesson id: {}", id);
        return ApiResponse.<TeacherLessonResponse>builder()
                .result(lessonService.publishLesson(id))
                .build();
    }

    @GetMapping("/{id}/analytics")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ApiResponse<LessonAnalyticsResponse> getLessonAnalytics(@PathVariable Integer id) {
        log.info("REST request to get analytics for lesson id: {}", id);
        return ApiResponse.<LessonAnalyticsResponse>builder()
                .result(lessonService.getLessonAnalytics(id))
                .build();
    }
}