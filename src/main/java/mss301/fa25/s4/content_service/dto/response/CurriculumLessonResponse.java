package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurriculumLessonResponse {
    Integer id;
    Integer chapterId;
    String chapterTitle;
    String title;
    String description;
    Integer orderIndex;
}