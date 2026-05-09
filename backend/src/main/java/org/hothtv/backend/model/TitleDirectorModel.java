package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "title_director")
@IdClass(TitleDirectorIdModel.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TitleDirectorModel {

    @Id
    @Column(name = "title_id")
    private Long titleId;

    @Id
    @Column(name = "person_id")
    private Long personId;
}
