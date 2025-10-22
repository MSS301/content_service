package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.service.LessonFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson-files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Lesson File Management", description = "APIs for managing lesson files")
public class LessonFileController {
    LessonFileService fileService;

    @PostMapping
    public ApiResponse<LessonFileResponse> uploadFile(@Valid @RequestBody LessonFileRequest request) {
        log.info("REST request to upload file");
        return ApiResponse.<LessonFileResponse>builder()
                .result(fileService.uploadFile(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonFileResponse> getFileById(@PathVariable Integer id) {
        log.info("REST request to get file by id: {}", id);
        return ApiResponse.<LessonFileResponse>builder()
                .result(fileService.getFileById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get lesson files", description = "Retrieve paginated list of lesson files with optional filtering")
    public PaginatedResponse<LessonFileResponse> getFiles(
            @Parameter(description = "Filter by lesson ID") @RequestParam(required = false) Integer lessonId,
            @Parameter(description = "Filter by uploader ID") @RequestParam(required = false) Integer uploaderId,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "uploadedAt") Pageable pageable) {
        log.info("REST request to get files with pagination");

        Page<LessonFileResponse> files;
        if (lessonId != null) {
            files = fileService.getFilesByLesson(lessonId, pageable);
        } else if (uploaderId != null) {
            files = fileService.getFilesByUploader(uploaderId, pageable);
        } else {
            return PaginatedResponse.<LessonFileResponse>builder()
                    .message("Please provide lessonId or uploaderId parameter")
                    .build();
        }

        return PaginatedResponse.of(files);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFile(@PathVariable Integer id) {
        log.info("REST request to delete file id: {}", id);
        fileService.deleteFile(id);
        return ApiResponse.<Void>builder()
                .message("File deleted successfully")
                .build();
    }
}