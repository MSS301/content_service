package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherLessonResponse {
    Integer id;
    Integer teacherId;
    String teacherName;
    Integer curriculumLessonId;
    String curriculumLessonTitle;
    String title;
    TeacherLessonStatus lessonStatus;
    Integer classId;
    String className;
    Integer viewCount;
    Double averageRating;
    Long ratingCount;
    Long commentCount;
    Integer fileCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Integer createdBy;
    Integer updatedBy;
}