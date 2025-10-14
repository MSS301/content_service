package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonAnalyticsResponse {
    Integer lessonId;
    String lessonTitle;
    Integer totalViews;
    Integer uniqueViewers;
    Integer totalFeedbacks;
    Double averageRating;
    Integer totalFiles;
    Long totalFileSize;
}