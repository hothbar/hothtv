package org.hothtv.backend.titles.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TitleType type;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

}
