package mss301.fa25.s4.content_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mss301.fa25.s4.content_service.enums.EntityStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Integer createdBy; // User ID from User Service

    @Column(name = "updated_by")
    private Integer updatedBy; // User ID from User Service

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private EntityStatus status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = EntityStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}