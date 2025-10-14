package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lesson_ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lesson_id", "student_id"})
})
public class LessonRating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    TeacherLesson lesson;

    @Column(name = "student_id", nullable = false)
    Integer studentId; // From User Service

    @Column(name = "rating", nullable = false)
    Integer rating; // 1-5
}