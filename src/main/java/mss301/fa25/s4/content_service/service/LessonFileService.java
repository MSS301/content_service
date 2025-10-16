package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.request.LessonFileRequest;
import mss301.fa25.s4.content_service.dto.response.LessonFileResponse;

import java.util.List;

public interface LessonFileService {
    LessonFileResponse uploadFile(LessonFileRequest request);

    LessonFileResponse getFileById(Integer id);

    List<LessonFileResponse> getFilesByLesson(Integer lessonId);

    List<LessonFileResponse> getFilesByUploader(Integer uploaderId);

    void deleteFile(Integer id);
}