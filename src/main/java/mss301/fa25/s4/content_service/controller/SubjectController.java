package mss301.fa25.s4.content_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.SubjectRequest;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.SubjectResponse;
import mss301.fa25.s4.content_service.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
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
    public ApiResponse<List<SubjectResponse>> getAllSubjects(@RequestParam(required = false) String name) {
        log.info("REST request to get all subjects");

        if (name != null && !name.isEmpty()) {
            return ApiResponse.<List<SubjectResponse>>builder()
                    .result(subjectService.searchSubjectsByName(name))
                    .build();
        }

        return ApiResponse.<List<SubjectResponse>>builder()
                .result(subjectService.getAllSubjects())
                .build();
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