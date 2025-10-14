package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByName(String name);
    List<Subject> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}