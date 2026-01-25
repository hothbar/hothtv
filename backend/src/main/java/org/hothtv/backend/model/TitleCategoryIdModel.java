package org.hothtv.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class TitleCategoryIdModel implements Serializable {

    @Column(name = "title_id")
    private Long titleId;

    @Column(name = "category_id")
    private Long categoryId;
}
