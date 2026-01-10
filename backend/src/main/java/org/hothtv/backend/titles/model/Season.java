package org.hothtv.backend.titles.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "season")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    @Column(name = "season_number", nullable = false)
    private Integer seasonNumber;
}
