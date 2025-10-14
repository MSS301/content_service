package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonViewResponse {
    Integer id;
    Integer lessonId;
    String lessonTitle;
    Integer userId;
    String userName; // Will be fetched from User Service
    Integer viewDuration;
    LocalDateTime viewedAt;
}