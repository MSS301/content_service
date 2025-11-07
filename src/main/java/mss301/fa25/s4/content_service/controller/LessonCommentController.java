package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.request.SelfLessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.service.LessonCommentService;
import mss301.fa25.s4.content_service.service.UserProfileClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Lesson Comment Management", description = "APIs for managing lesson comments")
public class LessonCommentController {
    LessonCommentService commentService;
    UserProfileClientService userProfileClientService;

    @PostMapping
    public ApiResponse<LessonCommentResponse> createComment(@Valid @RequestBody LessonCommentRequest request) {
        log.info("REST request to create comment");
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    @PostMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<LessonCommentResponse> createCommentSelf(@Valid @RequestBody SelfLessonCommentRequest request) {
        log.info("REST request to create comment (self)");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountId = authentication.getName();
        Integer studentId = userProfileClientService.getUserProfileId(accountId);
        
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.createCommentSelf(request, studentId))
                .build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public PaginatedResponse<LessonCommentResponse> getMyComments(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get my comments");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountId = authentication.getName();
        Integer studentId = userProfileClientService.getUserProfileId(accountId);
        
        Page<LessonCommentResponse> comments = commentService.getMyComments(studentId, pageable);
        return PaginatedResponse.of(comments);
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonCommentResponse> getCommentById(@PathVariable Integer id) {
        log.info("REST request to get comment by id: {}", id);
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.getCommentById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get lesson comments", description = "Retrieve paginated list of lesson comments with optional filtering")
    public PaginatedResponse<LessonCommentResponse> getComments(
            @Parameter(description = "Filter by lesson ID") @RequestParam(required = false) Integer lessonId,
            @Parameter(description = "Filter by student ID") @RequestParam(required = false) Integer studentId,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        log.info("REST request to get comments with pagination");

        Page<LessonCommentResponse> comments;
        if (lessonId != null) {
            comments = commentService.getCommentsByLesson(lessonId, pageable);
        } else if (studentId != null) {
            comments = commentService.getCommentsByStudent(studentId, pageable);
        } else {
            return PaginatedResponse.<LessonCommentResponse>builder()
                    .message("Please provide lessonId or studentId parameter")
                    .build();
        }

        return PaginatedResponse.of(comments);
    }

    @PutMapping("/{id}")
    public ApiResponse<LessonCommentResponse> updateComment(
            @PathVariable Integer id,
            @Valid @RequestBody LessonCommentRequest request) {
        log.info("REST request to update comment id: {}", id);
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.updateComment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable Integer id) {
        log.info("REST request to delete comment id: {}", id);
        commentService.deleteComment(id);
        return ApiResponse.<Void>builder()
                .message("Comment deleted successfully")
                .build();
    }
}