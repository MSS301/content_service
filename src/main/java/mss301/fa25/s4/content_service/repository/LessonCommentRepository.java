package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonComment;
import mss301.fa25.s4.content_service.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonCommentRepository extends JpaRepository<LessonComment, Integer> {
    List<LessonComment> findByLessonIdAndStatus(Integer lessonId, EntityStatus status);
    List<LessonComment> findByStudentIdAndStatus(Integer studentId, EntityStatus status);
    List<LessonComment> findByLessonIdAndStatusOrderByCreatedAtDesc(Integer lessonId, EntityStatus status);

    @Query("SELECT COUNT(lc) FROM LessonComment lc WHERE lc.lesson.id = :lessonId AND lc.status = :status")
    Long countByLessonIdAndStatus(Integer lessonId, EntityStatus status);
}