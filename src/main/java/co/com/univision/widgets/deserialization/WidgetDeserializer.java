package co.com.univision.widgets.deserialization;

import co.com.univision.widgets.domain.ContentDTO;
import co.com.univision.widgets.domain.WidgetDTO;
import co.com.univision.widgets.domain.ContentByTypeDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

/**
 * Class to customize deserialization for WidgetDTO
 */
public class WidgetDeserializer extends StdDeserializer<WidgetDTO> {


    public WidgetDeserializer() {
        this(null);
    }

    protected WidgetDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Mapping a jsonNode structure into a WidgetDTO object.
     *
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public WidgetDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        WidgetDTO widgets = new WidgetDTO();

        JsonNode widgetsNode = jsonNode.get("data").get("widgets");
        //get a stream from JsonNode structure
        Stream<JsonNode> widgetStream = StreamSupport.stream(widgetsNode.spliterator(), false);

         List<ContentByTypeDTO> contents = widgetStream.
                filter(w -> w.get("contents").size() > 0)
                .flatMap(w -> StreamSupport.stream(w.get("contents").spliterator(), false))
                .map(x -> new ContentDTO()
                        .builder()
                        .title(x.get("title").textValue())
                        .type(x.get("type").textValue())
                        .build())
                .collect(groupingBy(ContentDTO::getType,
                        mapping(ContentDTO::getTitle, toList())))
                .entrySet()
                .stream()
                .map(k -> new ContentByTypeDTO()
                        .builder()
                        .count(k.getValue().size())
                        .titles(k.getValue())
                        .type(k.getKey())
                        .build()
                ).collect(toList());
        widgets.setContents(contents);
        return widgets;
    }
}
