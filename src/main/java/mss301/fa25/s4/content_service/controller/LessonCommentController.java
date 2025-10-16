package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;
import mss301.fa25.s4.content_service.service.LessonCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonCommentController {
    LessonCommentService commentService;

    @PostMapping
    public ApiResponse<LessonCommentResponse> createComment(@Valid @RequestBody LessonCommentRequest request) {
        log.info("REST request to create comment");
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonCommentResponse> getCommentById(@PathVariable Integer id) {
        log.info("REST request to get comment by id: {}", id);
        return ApiResponse.<LessonCommentResponse>builder()
                .result(commentService.getCommentById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<LessonCommentResponse>> getComments(
            @RequestParam(required = false) Integer lessonId,
            @RequestParam(required = false) Integer studentId) {
        log.info("REST request to get comments");

        if (lessonId != null) {
            return ApiResponse.<List<LessonCommentResponse>>builder()
                    .result(commentService.getCommentsByLesson(lessonId))
                    .build();
        } else if (studentId != null) {
            return ApiResponse.<List<LessonCommentResponse>>builder()
                    .result(commentService.getCommentsByStudent(studentId))
                    .build();
        }

        return ApiResponse.<List<LessonCommentResponse>>builder()
                .message("Please provide lessonId or studentId parameter")
                .build();
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