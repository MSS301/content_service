package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "teacher_lessons")
public class TeacherLesson {
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

    @Column(name = "status", length = 50)
    String status; // DRAFT, GENERATING, GENERATED, PUBLISHED

    @Column(name = "class_id")
    Integer classId; // From User Service (optional)

    @Column(name = "view_count")
    Integer viewCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    List<LessonFile> lessonFiles;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    List<LessonFeedback> lessonFeedbacks;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    List<LessonView> lessonViews;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (viewCount == null) viewCount = 0;
        if (status == null) status = "DRAFT";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}