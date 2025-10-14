package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonFileResponse {
    Integer id;
    Integer lessonId;
    String lessonTitle;
    String fileName;
    String fileUrl;
    String mimeType;
    Long sizeBytes;
    Integer uploaderId;
    String uploaderName; // Will be fetched from User Service
    LocalDateTime createdAt;
}