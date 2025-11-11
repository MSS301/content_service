package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.request.SelfLessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonFileService {
    LessonFileResponse uploadFile(LessonFileRequest request);

    LessonFileResponse uploadFileSelf(SelfLessonFileRequest request, Integer uploaderId);

    LessonFileResponse getFileById(Integer id);

//    List<LessonFileResponse> getFilesByLesson(Integer lessonId);
    Page<LessonFileResponse> getFilesByLesson(Integer lessonId, Pageable pageable);

//    List<LessonFileResponse> getFilesByUploader(Integer uploaderId);
    Page<LessonFileResponse> getFilesByUploader(Integer uploaderId, Pageable pageable);

    void deleteFile(Integer id);
}