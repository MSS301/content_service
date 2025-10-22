package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.CurriculumLessonRequest;
import mss301.fa25.s4.content_service.dto.response.CurriculumLessonResponse;
import mss301.fa25.s4.content_service.entity.Chapter;
import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.CurriculumLessonMapper;
import mss301.fa25.s4.content_service.repository.ChapterRepository;
import mss301.fa25.s4.content_service.repository.CurriculumLessonRepository;
import mss301.fa25.s4.content_service.service.CurriculumLessonService;
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
public class CurriculumLessonServiceImpl implements CurriculumLessonService {
    CurriculumLessonRepository lessonRepository;
    ChapterRepository chapterRepository;
    CurriculumLessonMapper lessonMapper;

    @Override
    @Transactional
    public CurriculumLessonResponse createLesson(CurriculumLessonRequest request) {
        log.info("Creating new curriculum lesson: {}", request.getTitle());

        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        CurriculumLesson lesson = lessonMapper.toEntity(request);
        lesson.setChapter(chapter);
        lesson = lessonRepository.save(lesson);

        return lessonMapper.toResponse(lesson);
    }

    @Override
    public CurriculumLessonResponse getLessonById(Integer id) {
        log.info("Getting curriculum lesson by id: {}", id);
        CurriculumLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRICULUM_LESSON_NOT_FOUND));

        return lessonMapper.toResponse(lesson);
    }

    @Override
    public Page<CurriculumLessonResponse> getAllLessons(Pageable pageable) {
        log.info("Getting all curriculum lessons with pagination");
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::toResponse);
    }

    @Override
    public Page<CurriculumLessonResponse> getLessonsByChapter(Integer chapterId, Pageable pageable) {
        log.info("Getting curriculum lessons by chapter ID: {} with pagination", chapterId);
        return lessonRepository.findByChapterIdAndStatus(chapterId, EntityStatus.ACTIVE, pageable)
                .map(lessonMapper::toResponse);
    }

    @Override
    @Transactional
    public CurriculumLessonResponse updateLesson(Integer id, CurriculumLessonRequest request) {
        log.info("Updating curriculum lesson id: {}", id);
        CurriculumLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRICULUM_LESSON_NOT_FOUND));

        if (request.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(request.getChapterId())
                    .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
            lesson.setChapter(chapter);
        }

        lessonMapper.updateEntity(lesson, request);
        lesson = lessonRepository.save(lesson);

        return lessonMapper.toResponse(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Integer id) {
        log.info("Deleting curriculum lesson id: {}", id);
        CurriculumLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRICULUM_LESSON_NOT_FOUND));

        lesson.setStatus(EntityStatus.INACTIVE);
        lessonRepository.save(lesson);
    }
}