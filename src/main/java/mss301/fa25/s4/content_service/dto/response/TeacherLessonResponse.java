package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherLessonResponse {
    Integer id;
    Integer teacherId;
    String teacherName; // Will be fetched from User Service
    Integer curriculumLessonId;
    String curriculumLessonTitle;
    String title;
    String status;
    Integer classId;
    String className; // Will be fetched from User Service
    Integer viewCount;
    Double averageRating;
    Integer feedbackCount;
    Integer fileCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}