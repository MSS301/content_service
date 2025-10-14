package mss301.fa25.s4.content_service.enums;

public enum TeacherLessonStatus {
    DRAFT,          // Teacher is working on it
    GENERATING,     // AI is generating slides
    GENERATED,      // AI generation completed
    PUBLISHED,      // Available to students
    ARCHIVED        // No longer active
}