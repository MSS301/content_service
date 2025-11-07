package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherLessonRepository extends JpaRepository<TeacherLesson, Integer>, PagingAndSortingRepository<TeacherLesson, Integer> {
    List<TeacherLesson> findByTeacherIdAndStatus(Integer teacherId, EntityStatus status);
    Page<TeacherLesson> findByTeacherIdAndStatus(Integer teacherId, EntityStatus status, Pageable pageable);
    List<TeacherLesson> findByLessonStatusAndStatus(TeacherLessonStatus lessonStatus, EntityStatus status);
    Page<TeacherLesson> findByLessonStatusAndStatus(TeacherLessonStatus lessonStatus, EntityStatus status, Pageable pageable);
    List<TeacherLesson> findByTeacherIdAndLessonStatusAndStatus(Integer teacherId, TeacherLessonStatus lessonStatus, EntityStatus status);
    Page<TeacherLesson> findByTeacherIdAndLessonStatusAndStatus(Integer teacherId, TeacherLessonStatus lessonStatus, EntityStatus status, Pageable pageable);
    List<TeacherLesson> findByClassIdAndStatus(Integer classId, EntityStatus status);
    Page<TeacherLesson> findByClassIdAndStatus(Integer classId, EntityStatus status, Pageable pageable);
    Page<TeacherLesson> findByClassIdAndLessonStatusAndStatus(Integer classId, TeacherLessonStatus lessonStatus, EntityStatus status, Pageable pageable);

    @Query("SELECT tl FROM TeacherLesson tl WHERE tl.teacherId = :teacherId AND tl.status = :status ORDER BY tl.createdAt DESC")
    List<TeacherLesson> findByTeacherIdAndStatusOrderByCreatedAtDesc(Integer teacherId, EntityStatus status);
}