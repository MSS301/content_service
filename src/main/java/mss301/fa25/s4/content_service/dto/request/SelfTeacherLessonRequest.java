package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import mss301.fa25.s4.content_service.constant.TeacherLessonStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SelfTeacherLessonRequest {
    /**
     * Optional - Link to curriculum lesson if following standard curriculum
     * Can be null for custom teacher-created lessons
     */
    Integer curriculumLessonId;

    @NotBlank(message = "Lesson title is required")
    @Size(max = 255, message = "Lesson title must not exceed 255 characters")
    String title;

    TeacherLessonStatus lessonStatus;

    Integer classId;
}
