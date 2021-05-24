package co.com.univision.widgets.domain;

import co.com.univision.widgets.deserialization.WidgetDeserializer;
import co.com.univision.widgets.deserialization.WidgetDeserializerUsingCollections;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

/**
 * Model for Widgets Type
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonRootName(value = "data")
//@JsonDeserialize(using = WidgetDeserializerUsingCollections.class)
@JsonDeserialize(using = WidgetDeserializer.class)
public class WidgetDTO {
    private List<ContentByTypeDTO> contents;

}
