package org.hothtv.backend.people.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "title_director")
@IdClass(TitleDirectorId.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TitleDirector {

    @Id
    @Column(name = "title_id")
    private Long titleId;

    @Id
    @Column(name = "person_id")
    private Long personId;
}
