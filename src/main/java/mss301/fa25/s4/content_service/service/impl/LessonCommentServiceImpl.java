package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonCommentRequest;
import mss301.fa25.s4.content_service.dto.request.SelfLessonCommentRequest;
import mss301.fa25.s4.content_service.dto.response.LessonCommentResponse;
import mss301.fa25.s4.content_service.dto.response.UserProfileResponse;
import mss301.fa25.s4.content_service.entity.LessonComment;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.LessonCommentMapper;
import mss301.fa25.s4.content_service.repository.LessonCommentRepository;
import mss301.fa25.s4.content_service.repository.TeacherLessonRepository;
import mss301.fa25.s4.content_service.service.LessonCommentService;
import mss301.fa25.s4.content_service.service.UserProfileClientService;
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
public class LessonCommentServiceImpl implements LessonCommentService {
    LessonCommentRepository commentRepository;
    TeacherLessonRepository lessonRepository;
    LessonCommentMapper commentMapper;
    UserProfileClientService userProfileClientService;

    @Override
    @Transactional
    public LessonCommentResponse createComment(LessonCommentRequest request) {
        log.info("Creating comment for lesson id: {}", request.getLessonId());

        TeacherLesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        LessonComment comment = commentMapper.toEntity(request);
        comment.setLesson(lesson);
        comment = commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    @Override
    @Transactional
    public LessonCommentResponse createCommentSelf(SelfLessonCommentRequest request, Integer studentId) {
        log.info("Student {} commenting on lesson id: {}", studentId, request.getLessonId());

        TeacherLesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        LessonComment comment = LessonComment.builder()
                .lesson(lesson)
                .studentId(studentId)
                .comment(request.getComment())
                .build();

        comment = commentRepository.save(comment);
        return commentMapper.toResponse(comment);
    }

    @Override
    public Page<LessonCommentResponse> getMyComments(Integer studentId, Pageable pageable) {
        log.info("Getting comments for student id: {}", studentId);
        return commentRepository.findByStudentIdAndStatus(studentId, EntityStatus.ACTIVE, pageable)
                .map(commentMapper::toResponse);
    }

    @Override
    public LessonCommentResponse getCommentById(Integer id) {
        log.info("Getting comment by id: {}", id);
        LessonComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));

        return commentMapper.toResponse(comment);
    }

    @Override
    public Page<LessonCommentResponse> getCommentsByLesson(Integer lessonId, Pageable pageable) {
        log.info("Getting comments by lesson id: {} with pagination", lessonId);
        return commentRepository.findByLessonIdAndStatus(lessonId, EntityStatus.ACTIVE, pageable)
                .map(comment -> {
                    LessonCommentResponse response = commentMapper.toResponse(comment);
                    try {
                        UserProfileResponse profile = userProfileClientService.getUserProfileById(comment.getStudentId());
                        response.setStudentName(profile.getFullName());
                    } catch (Exception e) {
                        log.warn("Failed to fetch user profile for studentId: {}", comment.getStudentId());
                    }
                    return response;
                });
    }

    @Override
    public Page<LessonCommentResponse> getCommentsByStudent(Integer studentId, Pageable pageable) {
        log.info("Getting comments by student id: {} with pagination", studentId);
        return commentRepository.findByStudentIdAndStatus(studentId, EntityStatus.ACTIVE, pageable)
                .map(commentMapper::toResponse);
    }

    @Override
    @Transactional
    public LessonCommentResponse updateComment(Integer id, LessonCommentRequest request) {
        log.info("Updating comment id: {}", id);
        LessonComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));

        if (request.getComment() != null) {
            comment.setComment(request.getComment());
        }

        comment = commentRepository.save(comment);
        
        // Enrich response with user profile data
        LessonCommentResponse response = commentMapper.toResponse(comment);
        try {
            UserProfileResponse userProfile = userProfileClientService.getUserProfileById(comment.getStudentId());
            response.setStudentName(userProfile.getFullName());
        } catch (Exception e) {
            log.warn("Failed to fetch user profile for student ID: {}", comment.getStudentId(), e);
            response.setStudentName("User #" + comment.getStudentId());
        }
        
        return response;
    }

    @Override
    @Transactional
    public void deleteComment(Integer id) {
        log.info("Deleting comment id: {}", id);
        LessonComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));

        comment.setStatus(EntityStatus.INACTIVE);
        commentRepository.save(comment);
    }
}