package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.TeacherLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherLessonRepository extends JpaRepository<TeacherLesson, Integer> {
    List<TeacherLesson> findByTeacherId(Integer teacherId);
    List<TeacherLesson> findByStatus(String status);
    List<TeacherLesson> findByTeacherIdAndStatus(Integer teacherId, String status);
    List<TeacherLesson> findByClassId(Integer classId);

    @Query("SELECT tl FROM TeacherLesson tl WHERE tl.teacherId = :teacherId ORDER BY tl.createdAt DESC")
    List<TeacherLesson> findByTeacherIdOrderByCreatedAtDesc(Integer teacherId);
}