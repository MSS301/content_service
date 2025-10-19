package mss301.fa25.s4.content_service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.entity.TeacherLesson;
import mss301.fa25.s4.content_service.repository.TeacherLessonRepository;
import org.springframework.stereotype.Service;

/**
 * Security service for checking resource ownership
 * Used with @PreAuthorize annotations
 */
@Service("lessonSecurityService")  // Bean name must match the one in @PreAuthorize
@RequiredArgsConstructor
@Slf4j
public class LessonSecurityService {

    private final TeacherLessonRepository lessonRepository;

    /**
     * Check if the current user owns the lesson
     *
     * @param lessonId The lesson ID to check
     * @param userId The user ID from JWT token (authentication.principal)
     * @return true if user owns the lesson, false otherwise
     */
    public boolean isOwner(Integer lessonId, String userId) {
        log.debug("Checking if user {} owns lesson {}", userId, lessonId);

        try {
            // Convert userId from String to Integer if your teacherId is Integer
            Integer teacherId = Integer.parseInt(userId);

            TeacherLesson lesson = lessonRepository.findById(lessonId)
                    .orElse(null);

            if (lesson == null) {
                log.warn("Lesson {} not found", lessonId);
                return false;
            }

            boolean isOwner = lesson.getTeacherId().equals(teacherId);
            log.debug("User {} is{} owner of lesson {}", userId, isOwner ? "" : " NOT", lessonId);

            return isOwner;

        } catch (NumberFormatException e) {
            log.error("Invalid userId format: {}", userId);
            return false;
        }
    }

    /**
     * Check if user can view the lesson
     * (could be owner, or lesson is published, etc.)
     */
    public boolean canView(Integer lessonId, String userId) {
        log.debug("Checking if user {} can view lesson {}", userId, lessonId);

        TeacherLesson lesson = lessonRepository.findById(lessonId)
                .orElse(null);

        if (lesson == null) {
            return false;
        }

        try {
            Integer teacherId = Integer.parseInt(userId);

            // User can view if:
            // 1. They own it
            // 2. It's published
            // 3. They're enrolled in the class (would need additional check)
            return lesson.getTeacherId().equals(teacherId)
                    || lesson.getLessonStatus().toString().equals("PUBLISHED");

        } catch (NumberFormatException e) {
            log.error("Invalid userId format: {}", userId);
            return false;
        }
    }

    /**
     * Check if user can edit the lesson
     */
    public boolean canEdit(Integer lessonId, String userId) {
        // Only owner can edit
        return isOwner(lessonId, userId);
    }
}