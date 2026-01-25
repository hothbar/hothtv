package org.hothtv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "title_category")
public class TitleCategoryModel {

    @EmbeddedId
    private TitleCategoryIdModel id;
}
