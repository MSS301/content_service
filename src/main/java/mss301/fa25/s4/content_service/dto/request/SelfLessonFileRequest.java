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
public class SelfLessonFileRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotBlank(message = "File URL is required")
    String fileUrl;

    // Optional fields
    String fileName;
    String mimeType;
    Long sizeBytes;
}
