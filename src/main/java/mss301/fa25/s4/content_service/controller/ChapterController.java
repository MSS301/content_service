package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.constant.GradeLevel;
import mss301.fa25.s4.content_service.service.ChapterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterController {
    ChapterService chapterService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ChapterResponse> createChapter(@Valid @RequestBody ChapterRequest request) {
        log.info("REST request to create chapter");
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.createChapter(request))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ApiResponse<ChapterResponse> getChapterById(@PathVariable Integer id) {
        log.info("REST request to get chapter by id: {}", id);
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.getChapterById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ChapterResponse>> getAllChapters(
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) String grade) {
        log.info("REST request to get all chapters");

        if (subjectId != null && grade != null) {
            GradeLevel gradeLevel = GradeLevel.valueOf(grade);
            return ApiResponse.<List<ChapterResponse>>builder()
                    .result(chapterService.getChaptersBySubjectAndGrade(subjectId, gradeLevel))
                    .build();
        } else if (subjectId != null) {
            return ApiResponse.<List<ChapterResponse>>builder()
                    .result(chapterService.getChaptersBySubject(subjectId))
                    .build();
        } else if (grade != null) {
            GradeLevel gradeLevel = GradeLevel.valueOf(grade);
            return ApiResponse.<List<ChapterResponse>>builder()
                    .result(chapterService.getChaptersByGrade(gradeLevel))
                    .build();
        }

        return ApiResponse.<List<ChapterResponse>>builder()
                .result(chapterService.getAllChapters())
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ChapterResponse> updateChapter(
            @PathVariable Integer id,
            @Valid @RequestBody ChapterRequest request) {
        log.info("REST request to update chapter id: {}", id);
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.updateChapter(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteChapter(@PathVariable Integer id) {
        log.info("REST request to delete chapter id: {}", id);
        chapterService.deleteChapter(id);
        return ApiResponse.<Void>builder()
                .message("Chapter deleted successfully")
                .build();
    }
}