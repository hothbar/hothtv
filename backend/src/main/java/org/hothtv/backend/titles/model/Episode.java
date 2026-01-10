package org.hothtv.backend.titles.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "episode")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}
