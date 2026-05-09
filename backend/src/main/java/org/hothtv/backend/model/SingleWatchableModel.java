package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "single_watchable")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SingleWatchableModel {

    @Id
    @Column(name = "watchable_id")
    private Long watchableId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "watchable_id")
    private WatchableModel watchable;

    @Column(name = "single_title_id", nullable = false)
    private Long singleTitleId;
}
