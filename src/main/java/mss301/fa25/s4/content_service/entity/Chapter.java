package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    Grade grade;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "order_index")
    Integer orderIndex;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    List<CurriculumLesson> curriculumLessons;
}