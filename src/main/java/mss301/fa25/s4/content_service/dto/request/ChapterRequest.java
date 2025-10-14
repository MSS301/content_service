package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import mss301.fa25.s4.content_service.enums.GradeLevel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterRequest {
    @NotNull(message = "Subject ID is required")
    Integer subjectId;

    @NotNull(message = "Grade is required")
    GradeLevel grade;

    @NotBlank(message = "Chapter title is required")
    @Size(max = 255, message = "Chapter title must not exceed 255 characters")
    String title;

    Integer orderIndex;
}