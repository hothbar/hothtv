package org.hothtv.backend.people.model;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class TitleDirectorId implements Serializable {
    private Long titleId;
    private Long personId;
}
