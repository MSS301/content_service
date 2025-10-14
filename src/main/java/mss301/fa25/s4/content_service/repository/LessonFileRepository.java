package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonFileRepository extends JpaRepository<LessonFile, Integer> {
    List<LessonFile> findByLessonId(Integer lessonId);
    List<LessonFile> findByUploaderId(Integer uploaderId);
}