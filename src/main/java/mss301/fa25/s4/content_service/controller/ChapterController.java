package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.constant.GradeLevel;
import mss301.fa25.s4.content_service.service.ChapterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Chapter Management", description = "APIs for managing chapters")
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
    @Operation(summary = "Get all chapters", description = "Retrieve paginated list of chapters with optional filtering by subject and grade")
    public PaginatedResponse<ChapterResponse> getAllChapters(
            @Parameter(description = "Filter by subject ID") @RequestParam(required = false) Integer subjectId,
            @Parameter(description = "Filter by grade level") @RequestParam(required = false) String grade,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "orderIndex") Pageable pageable) {
        log.info("REST request to get all chapters with pagination");

        Page<ChapterResponse> chapters;
        if (subjectId != null && grade != null) {
            GradeLevel gradeLevel = GradeLevel.valueOf(grade);
            chapters = chapterService.getChaptersBySubjectAndGrade(subjectId, gradeLevel, pageable);
        } else if (subjectId != null) {
            chapters = chapterService.getChaptersBySubject(subjectId, pageable);
        } else if (grade != null) {
            GradeLevel gradeLevel = GradeLevel.valueOf(grade);
            chapters = chapterService.getChaptersByGrade(gradeLevel, pageable);
        } else {
            chapters = chapterService.getAllChapters(pageable);
        }

        return PaginatedResponse.of(chapters);
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