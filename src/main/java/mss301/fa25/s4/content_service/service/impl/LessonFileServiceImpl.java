package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;
import mss301.fa25.s4.content_service.entity.LessonFile;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.mapper.LessonFileMapper;
import mss301.fa25.s4.content_service.repository.LessonFileRepository;
import mss301.fa25.s4.content_service.repository.TeacherLessonRepository;
import mss301.fa25.s4.content_service.service.LessonFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonFileServiceImpl implements LessonFileService {
    LessonFileRepository fileRepository;
    TeacherLessonRepository lessonRepository;
    LessonFileMapper fileMapper;

    @Override
    @Transactional
    public LessonFileResponse uploadFile(LessonFileRequest request) {
        log.info("Uploading file for lesson id: {}", request.getLessonId());

        TeacherLesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_LESSON_NOT_FOUND));

        LessonFile file = fileMapper.toEntity(request);
        file.setLesson(lesson);
        file = fileRepository.save(file);

        return fileMapper.toResponse(file);
    }

    @Override
    public LessonFileResponse getFileById(Integer id) {
        log.info("Getting file by id: {}", id);
        LessonFile file = fileRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_FILE_NOT_FOUND));

        return fileMapper.toResponse(file);
    }

    @Override
    public List<LessonFileResponse> getFilesByLesson(Integer lessonId) {
        log.info("Getting files by lesson id: {}", lessonId);
        return fileRepository.findByLessonIdAndStatus(lessonId, EntityStatus.ACTIVE).stream()
                .map(fileMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonFileResponse> getFilesByUploader(Integer uploaderId) {
        log.info("Getting files by uploader id: {}", uploaderId);
        return fileRepository.findByUploaderIdAndStatus(uploaderId, EntityStatus.ACTIVE).stream()
                .map(fileMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFile(Integer id) {
        log.info("Deleting file id: {}", id);
        LessonFile file = fileRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_FILE_NOT_FOUND));

        file.setStatus(EntityStatus.INACTIVE);
        fileRepository.save(file);
    }
}