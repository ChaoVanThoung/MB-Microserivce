package co.istad.thoung.pipeline_service.stream;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlStringDeserializer<T> extends JsonDeserializer<T> {

    private final XmlMapper xmlMapper;
    private final Class<T>  targetClass;

    public XmlStringDeserializer(Class<T> targetClass) {
        this.targetClass = targetClass;
        this.xmlMapper = new XmlMapper();

        // configure XML mapper
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String xmlString = jsonParser.getText();

        // Handle null, empty, or Debezium unavailable values
        if (xmlString == null || "__debezium_unavailable_value".equals(xmlString)) {
            return null;
        }

        try{
            return xmlMapper.readValue(xmlString, targetClass);
        } catch (Exception e) {
            return null;
        }
    }
}
