package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenerateSlideRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotNull(message = "Teacher ID is required")
    Integer teacherId;

    String topic;

    String model; // "gpt-4", "claude-3"

    Double temperature;

    Integer maxTokens;
}