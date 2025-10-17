package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Chapter;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import mss301.fa25.s4.content_service.constant.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findBySubjectIdAndStatus(Integer subjectId, EntityStatus status);
    List<Chapter> findByGradeAndStatus(GradeLevel grade, EntityStatus status);
    List<Chapter> findBySubjectIdAndGradeAndStatus(Integer subjectId, GradeLevel grade, EntityStatus status);
    List<Chapter> findBySubjectIdAndStatusOrderByOrderIndexAsc(Integer subjectId, EntityStatus status);
}