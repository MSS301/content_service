package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;
import mss301.fa25.s4.content_service.service.LessonFileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson-files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
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
    public ApiResponse<List<LessonFileResponse>> getFiles(
            @RequestParam(required = false) Integer lessonId,
            @RequestParam(required = false) Integer uploaderId) {
        log.info("REST request to get files");

        if (lessonId != null) {
            return ApiResponse.<List<LessonFileResponse>>builder()
                    .result(fileService.getFilesByLesson(lessonId))
                    .build();
        } else if (uploaderId != null) {
            return ApiResponse.<List<LessonFileResponse>>builder()
                    .result(fileService.getFilesByUploader(uploaderId))
                    .build();
        }

        return ApiResponse.<List<LessonFileResponse>>builder()
                .message("Please provide lessonId or uploaderId parameter")
                .build();
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