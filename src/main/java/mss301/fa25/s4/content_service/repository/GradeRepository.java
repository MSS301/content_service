package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Optional<Grade> findByLevel(Integer level);
    boolean existsByLevel(Integer level);
}