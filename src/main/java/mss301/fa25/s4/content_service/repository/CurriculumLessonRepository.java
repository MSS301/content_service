package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import mss301.fa25.s4.content_service.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumLessonRepository extends JpaRepository<CurriculumLesson, Integer> {
    List<CurriculumLesson> findByChapterIdAndStatus(Integer chapterId, EntityStatus status);
    List<CurriculumLesson> findByChapterIdAndStatusOrderByOrderIndexAsc(Integer chapterId, EntityStatus status);
}