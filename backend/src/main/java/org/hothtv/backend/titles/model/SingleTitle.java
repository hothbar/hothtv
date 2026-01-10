package org.hothtv.backend.titles.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "single_title")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SingleTitle {

    @Id
    @Column(name = "title_id")
    private Long titleId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "title_id")
    private Title title;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}
