package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    Integer id;
    Integer subjectId;
    String subjectName;
    Integer gradeId;
    Integer gradeLevel;
    String title;
    Integer orderIndex;
    Integer lessonCount;
}