package co.istad.thoung.pipeline_service.stream;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "data")
public class XmlData {

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "role")
    private String role;
}