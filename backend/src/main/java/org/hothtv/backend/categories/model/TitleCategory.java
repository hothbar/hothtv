package org.hothtv.backend.categories.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "title_category")
@IdClass(TitleCategoryId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleCategory {

    @Id
    @Column(name = "title_id")
    private Long titleId;

    @Id
    @Column(name = "category_id")
    private Long categoryId;
}
