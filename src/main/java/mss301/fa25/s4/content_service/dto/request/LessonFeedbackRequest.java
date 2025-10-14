package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonFeedbackRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotNull(message = "Student ID is required")
    Integer studentId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    Integer rating;

    String comment;
}