package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import mss301.fa25.s4.content_service.constant.GradeLevel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chapters")
public class Chapter extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false, length = 20)
    GradeLevel grade;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "order_index")
    Integer orderIndex;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    List<CurriculumLesson> curriculumLessons;
}