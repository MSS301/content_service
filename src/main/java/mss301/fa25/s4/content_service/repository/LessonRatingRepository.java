package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonRating;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRatingRepository extends JpaRepository<LessonRating, Integer> {
    List<LessonRating> findByLessonIdAndStatus(Integer lessonId, EntityStatus status);
    List<LessonRating> findByStudentIdAndStatus(Integer studentId, EntityStatus status);
    Optional<LessonRating> findByLessonIdAndStudentIdAndStatus(Integer lessonId, Integer studentId, EntityStatus status);
    boolean existsByLessonIdAndStudentIdAndStatus(Integer lessonId, Integer studentId, EntityStatus status);

    @Query("SELECT AVG(lr.rating) FROM LessonRating lr WHERE lr.lesson.id = :lessonId AND lr.status = :status")
    Double getAverageRatingByLessonIdAndStatus(Integer lessonId, EntityStatus status);

    @Query("SELECT COUNT(lr) FROM LessonRating lr WHERE lr.lesson.id = :lessonId AND lr.status = :status")
    Long countByLessonIdAndStatus(Integer lessonId, EntityStatus status);
}