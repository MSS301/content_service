package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.SelfTeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonRequest;
import mss301.fa25.s4.content_service.dto.request.TeacherLessonUpdateRequest;
import mss301.fa25.s4.content_service.dto.response.LessonAnalyticsResponse;
import mss301.fa25.s4.content_service.dto.response.TeacherLessonResponse;
import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.TeacherLessonMapper;
import mss301.fa25.s4.content_service.repository.*;
import mss301.fa25.s4.content_service.service.TeacherLessonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TeacherLessonServiceImpl implements TeacherLessonService {
    TeacherLessonRepository lessonRepository;
    CurriculumLessonRepository curriculumLessonRepository;
    LessonRatingRepository ratingRepository;
    LessonCommentRepository commentRepository;
    LessonFileRepository fileRepository;
    TeacherLessonMapper lessonMapper;

    @Override
    @Transactional
    public TeacherLessonResponse createLesson(TeacherLessonRequest request) {
        log.info("Creating new teacher lesson: {}", request.getTitle());

        TeacherLesson lesson = lessonMapper.toEntity(request);

        if (request.getCurriculumLessonId() != null) {
            CurriculumLesson curriculumLesson = curriculumLessonRepository.findById(request.getCurriculumLessonId())
                    .orElseThrow(() -> new AppException(ErrorCode.CURRICULUM_LESSON_NOT_FOUND));
            lesson.setCurriculumLesson(curriculumLesson);
            log.info("Lesson linked to curriculum lesson ID: {}", request.getCurriculumLessonId());
        } else {
            log.info("Creating custom lesson without curriculum reference");
        }

        lesson = lessonRepository.save(lesson);
        return enrichLessonResponse(lessonMapper.toResponse(lesson));
    }

    @Override
    @Transactional
    public TeacherLessonResponse createSelfLesson(SelfTeacherLessonRequest request, Integer teacherId) {
        log.info("Creating new teacher lesson for teacher ID: {}", teacherId);

        TeacherLesson lesson = TeacherLesson.builder()
                .teacherId(teacherId)
                .title(request.getTitle())
                .lessonStatus(request.getLessonStatus() != null ? request.getLessonStatus() : TeacherLessonStatus.DRAFT)
                .classId(request.getClassId())
                .viewCount(0)
                .build();

        if (request.getCurriculumLessonId() != null) {
            CurriculumLesson curriculumLesson = curriculumLessonRepository.findById(request.getCurriculumLessonId())
                    .orElseThrow(() -> new AppException(ErrorCode.CURRICULUM_LESSON_NOT_FOUND));
            lesson.setCurriculumLesson(curriculumLesson);
            log.info("Lesson linked to curriculum lesson ID: {}", request.getCurriculumLessonId());
        } else {
            log.info("Creating custom lesson without curriculum reference");
        }

        lesson = lessonRepository.save(lesson);
        return enrichLessonResponse(lessonMapper.toResponse(lesson));
    }

    @Override
    public Page<TeacherLessonResponse> getMyLessons(Integer teacherId, Pageable pageable) {
        log.info("Getting lessons for teacher ID: {}", teacherId);
        return lessonRepository.findByTeacherIdAndStatus(teacherId, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    public TeacherLessonResponse getLessonById(Integer id) {
        log.info("Getting teacher lesson by id: {}", id);
        TeacherLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        return enrichLessonResponse(lessonMapper.toResponse(lesson));
    }

    @Override
    public Page<TeacherLessonResponse> getAllLessons(Pageable pageable) {
        log.info("Getting all teacher lessons with pagination");
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    public Page<TeacherLessonResponse> getLessonsByTeacher(Integer teacherId, Pageable pageable) {
        log.info("Getting teacher lessons by teacher ID: {} with pagination", teacherId);
        return lessonRepository.findByTeacherIdAndStatus(teacherId, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    public Page<TeacherLessonResponse> getLessonsByStatus(TeacherLessonStatus status, Pageable pageable) {
        log.info("Getting teacher lessons by status: {} with pagination", status);
        return lessonRepository.findByLessonStatusAndStatus(status, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    public Page<TeacherLessonResponse> getLessonsByClass(Integer classId, Pageable pageable) {
        log.info("Getting teacher lessons by class ID: {} with pagination", classId);
        return lessonRepository.findByClassIdAndStatus(classId, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    public Page<TeacherLessonResponse> getPublishedLessonsByClass(Integer classId, Pageable pageable) {
        log.info("Getting published teacher lessons for class ID: {} with pagination", classId);
        return lessonRepository.findByClassIdAndLessonStatusAndStatus(
                        classId, TeacherLessonStatus.PUBLISHED, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse)
                .map(this::enrichLessonResponse);
    }

    @Override
    @Transactional
    public TeacherLessonResponse updateLesson(Integer id, TeacherLessonUpdateRequest request) {
        log.info("Updating teacher lesson id: {}", id);
        TeacherLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        if (request.getTitle() != null) {
            lesson.setTitle(request.getTitle());
        }
        if (request.getStatus() != null) {
            try {
                lesson.setLessonStatus(TeacherLessonStatus.valueOf(request.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.INVALID_LESSON_STATUS);
            }
        }
        if (request.getClassId() != null) {
            lesson.setClassId(request.getClassId());
        }

        lesson = lessonRepository.save(lesson);
        return enrichLessonResponse(lessonMapper.toResponse(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(Integer id) {
        log.info("Deleting teacher lesson id: {}", id);
        TeacherLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        lesson.setStatus(EntityStatus.INACTIVE);
        lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public void incrementViewCount(Integer id) {
        log.info("Incrementing view count for lesson id: {}", id);
        TeacherLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        lesson.setViewCount(lesson.getViewCount() + 1);
        lessonRepository.save(lesson);
    }

    @Override
    public LessonAnalyticsResponse getLessonAnalytics(Integer id) {
        log.info("Getting analytics for lesson id: {}", id);
        TeacherLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        Double avgRating = ratingRepository.getAverageRatingByLessonIdAndStatus(id, EntityStatus.ACTIVE);
        Long ratingCount = ratingRepository.countByLessonIdAndStatus(id, EntityStatus.ACTIVE);
        Long commentCount = commentRepository.countByLessonIdAndStatus(id, EntityStatus.ACTIVE);
        int fileCount = fileRepository.findByLessonIdAndStatus(id, EntityStatus.ACTIVE).size();
        long totalFileSize = fileRepository.findByLessonIdAndStatus(id, EntityStatus.ACTIVE).stream()
                .mapToLong(file -> file.getSizeBytes() != null ? file.getSizeBytes() : 0L)
                .sum();

        return LessonAnalyticsResponse.builder()
                .lessonId(lesson.getId())
                .lessonTitle(lesson.getTitle())
                .totalViews(lesson.getViewCount())
                .uniqueViewers(lesson.getViewCount()) // TODO: Implement unique viewers tracking
                .totalFeedbacks(ratingCount.intValue())
                .averageRating(avgRating)
                .totalFiles(fileCount)
                .totalFileSize(totalFileSize)
                .build();
    }

    private TeacherLessonResponse enrichLessonResponse(TeacherLessonResponse response) {
        // Get statistics
        Double avgRating = ratingRepository.getAverageRatingByLessonIdAndStatus(
                response.getId(), EntityStatus.ACTIVE);
        Long ratingCount = ratingRepository.countByLessonIdAndStatus(
                response.getId(), EntityStatus.ACTIVE);
        Long commentCount = commentRepository.countByLessonIdAndStatus(
                response.getId(), EntityStatus.ACTIVE);

        response.setAverageRating(avgRating);
        response.setRatingCount(ratingCount);
        response.setCommentCount(commentCount);

        // TODO: Fetch teacher name and class name from User Service via Feign

        return response;
    }
}