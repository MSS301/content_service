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
@Table(name = "lesson_files")
public class LessonFile  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    TeacherLesson lesson;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_url", columnDefinition = "TEXT")
    String fileUrl; // S3/MinIO URL

    @Column(name = "mime_type", length = 100)
    String mimeType;

    @Column(name = "size_bytes")
    Long sizeBytes;

    @Column(name = "uploader_id")
    Integer uploaderId; // From User Service

}