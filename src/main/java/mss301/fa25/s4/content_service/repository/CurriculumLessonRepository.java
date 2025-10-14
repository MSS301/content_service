package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumLessonRepository extends JpaRepository<CurriculumLesson, Integer> {
    List<CurriculumLesson> findByChapterId(Integer chapterId);
    List<CurriculumLesson> findByChapterIdOrderByOrderIndexAsc(Integer chapterId);
}