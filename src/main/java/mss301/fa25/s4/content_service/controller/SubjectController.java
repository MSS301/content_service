package mss301.fa25.s4.content_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.SubjectRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.PaginatedResponse;
import mss301.fa25.s4.content_service.dto.response.SubjectResponse;
import mss301.fa25.s4.content_service.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Subject Management", description = "APIs for managing subjects")
public class SubjectController {
    SubjectService subjectService;

    @PostMapping
    public ApiResponse<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest request) {
        log.info("REST request to create subject");
        return ApiResponse.<SubjectResponse>builder()
                .result(subjectService.createSubject(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SubjectResponse> getSubjectById(@PathVariable Integer id) {
        log.info("REST request to get subject by id: {}", id);
        return ApiResponse.<SubjectResponse>builder()
                .result(subjectService.getSubjectById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all subjects", description = "Retrieve paginated list of subjects with optional name filtering")
    public PaginatedResponse<SubjectResponse> getAllSubjects(
            @Parameter(description = "Filter by subject name (partial match)") @RequestParam(required = false) String name,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        log.info("REST request to get all subjects with pagination");

        Page<SubjectResponse> subjects;
        if (name != null && !name.isEmpty()) {
            subjects = subjectService.searchSubjectsByName(name, pageable);
        } else {
            subjects = subjectService.getAllSubjects(pageable);
        }

        return PaginatedResponse.of(subjects);
    }

    @PutMapping("/{id}")
    public ApiResponse<SubjectResponse> updateSubject(
            @PathVariable Integer id,
            @Valid @RequestBody SubjectRequest request) {
        log.info("REST request to update subject id: {}", id);
        return ApiResponse.<SubjectResponse>builder()
                .result(subjectService.updateSubject(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSubject(@PathVariable Integer id) {
        log.info("REST request to delete subject id: {}", id);
        subjectService.deleteSubject(id);
        return ApiResponse.<Void>builder()
                .message("Subject deleted successfully")
                .build();
    }
}