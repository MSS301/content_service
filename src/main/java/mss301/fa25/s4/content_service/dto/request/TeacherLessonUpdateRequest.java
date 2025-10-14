package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherLessonUpdateRequest {
    @Size(max = 255, message = "Lesson title must not exceed 255 characters")
    String title;

    String status;

    Integer classId;
}