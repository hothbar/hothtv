package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "episode_watchable")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EpisodeWatchableModel {

    @Id
    @Column(name = "watchable_id")
    private Long watchableId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "watchable_id")
    private WatchableModel watchable;

    @Column(name = "episode_id", nullable = false)
    private Long episodeId;
}
