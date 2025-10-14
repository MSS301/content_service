package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonViewRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotNull(message = "User ID is required")
    Integer userId;

    Integer viewDuration; // in seconds
}