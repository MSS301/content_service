package mss301.fa25.s4.content_service.repository;

import mss301.fa25.s4.content_service.entity.Subject;
import mss301.fa25.s4.content_service.constant.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>, PagingAndSortingRepository<Subject, Integer> {
    Optional<Subject> findByNameAndStatus(String name, EntityStatus status);
    List<Subject> findByStatus(EntityStatus status);
    Page<Subject> findByStatus(EntityStatus status, Pageable pageable);
    List<Subject> findByNameContainingIgnoreCaseAndStatus(String name, EntityStatus status);
    Page<Subject> findByNameContainingIgnoreCaseAndStatus(String name, EntityStatus status, Pageable pageable);
    boolean existsByNameAndStatus(String name, EntityStatus status);
}