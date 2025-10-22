package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.LessonFile;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonFileRepository extends JpaRepository<LessonFile, Integer>, PagingAndSortingRepository<LessonFile, Integer> {
    List<LessonFile> findByLessonIdAndStatus(Integer lessonId, EntityStatus status);
    Page<LessonFile> findByLessonIdAndStatus(Integer lessonId, EntityStatus status, Pageable pageable);
    List<LessonFile> findByUploaderIdAndStatus(Integer uploaderId, EntityStatus status);
    Page<LessonFile> findByUploaderIdAndStatus(Integer uploaderId, EntityStatus status, Pageable pageable);
}