package co.istad.thoung.pipeline_service.stream;


public class XmlDataDeserializer extends XmlStringDeserializer<XmlData> {
    public XmlDataDeserializer() {
        super(XmlData.class);
    }
}
