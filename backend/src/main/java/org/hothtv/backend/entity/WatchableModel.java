package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hothtv.backend.model.WatchableKindModel;

@Entity
@Table(name = "watchable")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WatchableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchable_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false, length = 20)
    private WatchableKindModel kind;
}
