package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonViewRepository extends JpaRepository<LessonView, Integer> {
    List<LessonView> findByLessonId(Integer lessonId);
    List<LessonView> findByUserId(Integer userId);

    @Query("SELECT COUNT(lv) FROM LessonView lv WHERE lv.lesson.id = :lessonId")
    Long countByLessonId(Integer lessonId);
}