package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.CurriculumLesson;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumLessonRepository extends JpaRepository<CurriculumLesson, Integer>, PagingAndSortingRepository<CurriculumLesson, Integer> {
    List<CurriculumLesson> findByChapterIdAndStatus(Integer chapterId, EntityStatus status);
    Page<CurriculumLesson> findByChapterIdAndStatus(Integer chapterId, EntityStatus status, Pageable pageable);
    List<CurriculumLesson> findByChapterIdAndStatusOrderByOrderIndexAsc(Integer chapterId, EntityStatus status);
}