package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonRatingRequest;
import mss301.fa25.s4.content_service.dto.request.SelfLessonRatingRequest;
import mss301.fa25.s4.content_service.dto.response.LessonRatingResponse;
import mss301.fa25.s4.content_service.entity.LessonRating;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.LessonRatingMapper;
import mss301.fa25.s4.content_service.repository.LessonRatingRepository;
import mss301.fa25.s4.content_service.repository.TeacherLessonRepository;
import mss301.fa25.s4.content_service.service.LessonRatingService;
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
public class LessonRatingServiceImpl implements LessonRatingService {
    LessonRatingRepository ratingRepository;
    TeacherLessonRepository lessonRepository;
    LessonRatingMapper ratingMapper;

    @Override
    @Transactional
    public LessonRatingResponse rateLesson(LessonRatingRequest request) {
        log.info("Creating rating for lesson id: {}", request.getLessonId());

        if (ratingRepository.existsByLessonIdAndStudentIdAndStatus(
                request.getLessonId(), request.getStudentId(), EntityStatus.ACTIVE)) {
            throw new AppException(ErrorCode.RATING_ALREADY_EXISTS);
        }

        TeacherLesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        LessonRating rating = ratingMapper.toEntity(request);
        rating.setLesson(lesson);
        rating = ratingRepository.save(rating);

        return ratingMapper.toResponse(rating);
    }

    @Override
    @Transactional
    public LessonRatingResponse rateLessonSelf(SelfLessonRatingRequest request, Integer studentId) {
        log.info("Student {} rating lesson id: {}", studentId, request.getLessonId());

        if (ratingRepository.existsByLessonIdAndStudentIdAndStatus(
                request.getLessonId(), studentId, EntityStatus.ACTIVE)) {
            throw new AppException(ErrorCode.RATING_ALREADY_EXISTS);
        }

        TeacherLesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        LessonRating rating = LessonRating.builder()
                .lesson(lesson)
                .studentId(studentId)
                .rating(request.getRating())
                .build();

        rating = ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public Page<LessonRatingResponse> getMyRatings(Integer studentId, Pageable pageable) {
        log.info("Getting ratings for student id: {}", studentId);
        return ratingRepository.findByStudentIdAndStatus(studentId, EntityStatus.ACTIVE, pageable)
                .map(ratingMapper::toResponse);
    }

    @Override
    public LessonRatingResponse getRatingById(Integer id) {
        log.info("Getting rating by id: {}", id);
        LessonRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_RATING_NOT_FOUND));

        return ratingMapper.toResponse(rating);
    }

    @Override
    public Page<LessonRatingResponse> getRatingsByLesson(Integer lessonId, Pageable pageable) {
        log.info("Getting ratings by lesson id: {} with pagination", lessonId);
        return ratingRepository.findByLessonIdAndStatus(lessonId, EntityStatus.ACTIVE, pageable)
                .map(ratingMapper::toResponse);
    }

    @Override
    public Page<LessonRatingResponse> getRatingsByStudent(Integer studentId, Pageable pageable) {
        log.info("Getting ratings by student id: {} with pagination", studentId);
        return ratingRepository.findByStudentIdAndStatus(studentId, EntityStatus.ACTIVE, pageable)
                .map(ratingMapper::toResponse);
    }

    @Override
    @Transactional
    public LessonRatingResponse updateRating(Integer id, LessonRatingRequest request) {
        log.info("Updating rating id: {}", id);
        LessonRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_RATING_NOT_FOUND));

        if (request.getRating() != null) {
            rating.setRating(request.getRating());
        }

        rating = ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    @Transactional
    public void deleteRating(Integer id) {
        log.info("Deleting rating id: {}", id);
        LessonRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_RATING_NOT_FOUND));

        rating.setStatus(EntityStatus.INACTIVE);
        ratingRepository.save(rating);
    }
}