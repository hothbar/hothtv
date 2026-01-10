package org.hothtv.backend.watchables.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "watchable")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Watchable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchable_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false, length = 20)
    private WatchableKind kind;
}
