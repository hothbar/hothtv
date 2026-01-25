package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "season")
@Data @NoArgsConstructor @AllArgsConstructor
public class SeasonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    private Long id;

    @Column(name = "title_id", nullable = false)
    private Long titleId;

    @Column(name = "season_number", nullable = false)
    private Integer seasonNumber;

}