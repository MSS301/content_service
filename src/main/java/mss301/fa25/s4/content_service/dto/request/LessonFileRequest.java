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
public class LessonFileRequest {
    @NotNull(message = "Lesson ID is required")
    Integer lessonId;

    @NotBlank(message = "File name is required")
    String fileName;

    @NotBlank(message = "File URL is required")
    String fileUrl;

    String mimeType;

    Long sizeBytes;

    @NotNull(message = "Uploader ID is required")
    Integer uploaderId;
}