package org.hothtv.backend.categories.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TitleCategoryId implements Serializable {
    private Long titleId;
    private Long categoryId;
}
