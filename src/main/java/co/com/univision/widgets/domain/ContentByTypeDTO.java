package co.com.univision.widgets.domain;


import lombok.*;

import java.util.List;

/**
 * Model For Contens grouping by type.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ContentByTypeDTO {

    public int count;
    public String type;
    public List<String> titles;

    public ContentByTypeDTO(ContentByTypeDTO o) {
        this.count = o.getCount();
        this.type = o.getType();
        this.titles = o.getTitles();
    }
}
