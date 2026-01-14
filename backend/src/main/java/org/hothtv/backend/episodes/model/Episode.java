package org.hothtv.backend.episodes.model;

import jakarta.persistence.*;
import lombok.*;
import org.hothtv.backend.seasons.model.Season;

@Entity
@Table(name = "episode")
@Data @NoArgsConstructor @AllArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    @Column(name = "season_id", nullable = false)
    private Long seasonId;

    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}