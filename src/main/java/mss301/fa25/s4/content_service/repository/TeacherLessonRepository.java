package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherLessonRepository extends JpaRepository<TeacherLesson, Integer> {
    List<TeacherLesson> findByTeacherIdAndStatus(Integer teacherId, EntityStatus status);
    List<TeacherLesson> findByLessonStatusAndStatus(TeacherLessonStatus lessonStatus, EntityStatus status);
    List<TeacherLesson> findByTeacherIdAndLessonStatusAndStatus(Integer teacherId, TeacherLessonStatus lessonStatus, EntityStatus status);
    List<TeacherLesson> findByClassIdAndStatus(Integer classId, EntityStatus status);

    @Query("SELECT tl FROM TeacherLesson tl WHERE tl.teacherId = :teacherId AND tl.status = :status ORDER BY tl.createdAt DESC")
    List<TeacherLesson> findByTeacherIdAndStatusOrderByCreatedAtDesc(Integer teacherId, EntityStatus status);
}