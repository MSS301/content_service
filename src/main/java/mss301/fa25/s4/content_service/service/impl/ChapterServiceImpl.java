package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.ChapterRequest;
import mss301.fa25.s4.content_service.dto.response.ChapterResponse;
import mss301.fa25.s4.content_service.entity.Chapter;
import mss301.fa25.s4.content_service.entity.Subject;
import mss301.fa25.s4.content_service.enums.EntityStatus;
import mss301.fa25.s4.content_service.enums.GradeLevel;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.ChapterMapper;
import mss301.fa25.s4.content_service.repository.ChapterRepository;
import mss301.fa25.s4.content_service.repository.SubjectRepository;
import mss301.fa25.s4.content_service.service.ChapterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterServiceImpl implements ChapterService {
    ChapterRepository chapterRepository;
    SubjectRepository subjectRepository;
    ChapterMapper chapterMapper;

    @Override
    @Transactional
    public ChapterResponse createChapter(ChapterRequest request) {
        log.info("Creating new chapter: {}", request.getTitle());

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        Chapter chapter = chapterMapper.toEntity(request);
        chapter.setSubject(subject);
        chapter = chapterRepository.save(chapter);

        return chapterMapper.toResponse(chapter);
    }

    @Override
    public ChapterResponse getChapterById(Integer id) {
        log.info("Getting chapter by id: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        return chapterMapper.toResponse(chapter);
    }

    @Override
    public List<ChapterResponse> getAllChapters() {
        log.info("Getting all chapters");
        return chapterRepository.findAll().stream()
                .map(chapterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterResponse> getChaptersBySubject(Integer subjectId) {
        log.info("Getting chapters by subject ID: {}", subjectId);
        return chapterRepository.findBySubjectIdAndStatusOrderByOrderIndexAsc(subjectId, EntityStatus.ACTIVE).stream()
                .map(chapterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterResponse> getChaptersByGrade(GradeLevel grade) {
        log.info("Getting chapters by grade: {}", grade);
        return chapterRepository.findByGradeAndStatus(grade, EntityStatus.ACTIVE).stream()
                .map(chapterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterResponse> getChaptersBySubjectAndGrade(Integer subjectId, GradeLevel grade) {
        log.info("Getting chapters by subject ID: {} and grade: {}", subjectId, grade);
        return chapterRepository.findBySubjectIdAndGradeAndStatus(subjectId, grade, EntityStatus.ACTIVE).stream()
                .map(chapterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChapterResponse updateChapter(Integer id, ChapterRequest request) {
        log.info("Updating chapter id: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
            chapter.setSubject(subject);
        }

        chapterMapper.updateEntity(chapter, request);
        chapter = chapterRepository.save(chapter);

        return chapterMapper.toResponse(chapter);
    }

    @Override
    @Transactional
    public void deleteChapter(Integer id) {
        log.info("Deleting chapter id: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        chapter.setStatus(EntityStatus.INACTIVE);
        chapterRepository.save(chapter);
    }
}