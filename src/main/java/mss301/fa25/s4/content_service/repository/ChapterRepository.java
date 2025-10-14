package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findBySubjectId(Integer subjectId);
    List<Chapter> findByGradeId(Integer gradeId);
    List<Chapter> findBySubjectIdAndGradeId(Integer subjectId, Integer gradeId);
    List<Chapter> findBySubjectIdOrderByOrderIndexAsc(Integer subjectId);
}