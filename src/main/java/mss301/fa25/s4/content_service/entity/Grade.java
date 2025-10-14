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
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "level", nullable = false)
    Integer level; // 1-12

    @Column(name = "name", length = 50)
    String name;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
    List<Chapter> chapters;
}
