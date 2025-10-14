package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonFile;
import mss301.fa25.s4.content_service.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonFileRepository extends JpaRepository<LessonFile, Integer> {
    List<LessonFile> findByLessonIdAndStatus(Integer lessonId, EntityStatus status);
    List<LessonFile> findByUploaderIdAndStatus(Integer uploaderId, EntityStatus status);
}