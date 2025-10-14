package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import mss301.fa25.s4.content_service.enums.TeacherLessonStatus;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "teacher_lessons")
public class TeacherLesson extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "teacher_id", nullable = false)
    Integer teacherId; // From User Service

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_lesson_id")
    CurriculumLesson curriculumLesson;

    @Column(name = "title")
    String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_status", length = 20, nullable = false)
    TeacherLessonStatus lessonStatus;

    @Column(name = "class_id")
    Integer classId; // From User Service (optional)

    @Column(name = "view_count")
    Integer viewCount;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    List<LessonFile> lessonFiles;


    protected void onCreate() {
        super.onCreate();
        if (viewCount == null) viewCount = 0;
        if (lessonStatus == null) lessonStatus = TeacherLessonStatus.DRAFT;
    }

}