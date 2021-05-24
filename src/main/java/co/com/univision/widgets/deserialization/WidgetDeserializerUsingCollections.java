package co.com.univision.widgets.deserialization;

import co.com.univision.widgets.domain.ContentByTypeDTO;
import co.com.univision.widgets.domain.WidgetDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import java.util.*;

import static java.util.Arrays.asList;


/**
 * Class to customize deserialization for WidgetDTO
 */
public class WidgetDeserializerUsingCollections extends StdDeserializer<WidgetDTO> {


    public WidgetDeserializerUsingCollections() {
        this(null);
    }

    protected WidgetDeserializerUsingCollections(Class<?> vc) {
        super(vc);
    }

    private final ThreadLocal<Map<String, ContentByTypeDTO>> cache = new ThreadLocal<Map<String, ContentByTypeDTO>>() {
        @Override
        protected Map<String, ContentByTypeDTO> initialValue() {
            return new HashMap<>();
        }
    };

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

        JsonNode widgets = jsonNode.get("data").get("widgets");

        if (widgets.isArray()) {
            for(JsonNode w : widgets ) {
                getContent(w);
            }
        } else {
            getContent(widgets);
        }
        List<ContentByTypeDTO> values = new ArrayList<ContentByTypeDTO>(cache.get().values());
        return new WidgetDTO().builder().contents(values).build();
    }

    private void getContent(JsonNode widget) {
        JsonNode contents = widget.get("contents");
        if (contents.isArray()) {
            for(JsonNode w : contents ) {
                getData(w);
            }
        } else {
             getData(contents);
        }
    }

    private void getData(JsonNode node) {
        if (!node.isNull()) {
            fetchOrCreate(node.get("type").textValue(), node.get("title").textValue());
        }
    }

    private void fetchOrCreate(final String type, final String title) {
        ContentByTypeDTO content = cache.get().get(type);
        if (content == null) {
            content = new ContentByTypeDTO();
            content.setType(type);
            content.setCount(1);
            content.setTitles(asList(title));
            cache.get().put(type, content);
        }else {
            content.setCount(content.count+1);
            List<String> temp =new ArrayList<>(content.getTitles()); ;
            Collections.addAll(temp, title);
            content.setTitles(temp);
        }
    }
}
