package org.hothtv.backend.model;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class TitleCastIdModel implements Serializable {
    private Long titleId;
    private Long personId;
}
