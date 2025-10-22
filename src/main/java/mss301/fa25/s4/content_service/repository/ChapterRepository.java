package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Chapter;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.constant.GradeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer>, PagingAndSortingRepository<Chapter, Integer> {
    List<Chapter> findBySubjectIdAndStatus(Integer subjectId, EntityStatus status);
    Page<Chapter> findBySubjectIdAndStatus(Integer subjectId, EntityStatus status, Pageable pageable);
    List<Chapter> findByGradeAndStatus(GradeLevel grade, EntityStatus status);
    Page<Chapter> findByGradeAndStatus(GradeLevel grade, EntityStatus status, Pageable pageable);
    List<Chapter> findBySubjectIdAndGradeAndStatus(Integer subjectId, GradeLevel grade, EntityStatus status);
    Page<Chapter> findBySubjectIdAndGradeAndStatus(Integer subjectId, GradeLevel grade, EntityStatus status, Pageable pageable);
    List<Chapter> findBySubjectIdAndStatusOrderByOrderIndexAsc(Integer subjectId, EntityStatus status);
}