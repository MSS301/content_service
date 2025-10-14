package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonCommentRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotNull(message = "Student ID is required")
    Integer studentId;

    @NotBlank(message = "Comment is required")
    String comment;
}