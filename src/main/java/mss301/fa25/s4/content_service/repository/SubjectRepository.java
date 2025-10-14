package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Subject;
import mss301.fa25.s4.content_service.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByNameAndStatus(String name, EntityStatus status);
    List<Subject> findByStatus(EntityStatus status);
    List<Subject> findByNameContainingIgnoreCaseAndStatus(String name, EntityStatus status);
    boolean existsByNameAndStatus(String name, EntityStatus status);
}