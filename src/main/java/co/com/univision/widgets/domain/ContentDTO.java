package co.com.univision.widgets.domain;

import lombok.*;

/**
 * Model for contents Type
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ContentDTO {
    private String type;
    private String title;
}
