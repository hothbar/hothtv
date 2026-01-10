package org.hothtv.backend.people.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "title_cast")
@IdClass(TitleCastId.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TitleCast {

    @Id
    @Column(name = "title_id")
    private Long titleId;

    @Id
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "billing_order")
    private Integer billingOrder;
}
