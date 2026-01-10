package org.hothtv.backend.watchhistory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "watch_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "watchable_id", nullable = false)
    private Long watchableId;

    @Column(name = "watched_at", nullable = false, insertable = false, updatable = false)
    private Instant watchedAt;

    @Column(name = "progress_seconds", nullable = false)
    private Integer progressSeconds = 0;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;
}
