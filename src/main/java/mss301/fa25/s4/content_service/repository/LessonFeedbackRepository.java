package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonFeedbackRepository extends JpaRepository<LessonFeedback, Integer> {
    List<LessonFeedback> findByLessonId(Integer lessonId);
    List<LessonFeedback> findByStudentId(Integer studentId);
    Optional<LessonFeedback> findByLessonIdAndStudentId(Integer lessonId, Integer studentId);

    @Query("SELECT AVG(lf.rating) FROM LessonFeedback lf WHERE lf.lesson.id = :lessonId")
    Double getAverageRatingByLessonId(Integer lessonId);
}