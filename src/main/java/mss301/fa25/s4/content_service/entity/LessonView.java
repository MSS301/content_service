package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lesson_views")
public class LessonView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    TeacherLesson lesson;

    @Column(name = "user_id", nullable = false)
    Integer userId; // From User Service

    @Column(name = "view_duration")
    Integer viewDuration; // seconds

    @Column(name = "viewed_at", nullable = false)
    LocalDateTime viewedAt;

    @PrePersist
    protected void onCreate() {
        viewedAt = LocalDateTime.now();
    }
}