package co.istad.thoung.pipeline_service.stream;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StreamConfig {

    // Supplier for producing message into kafka topic
    // Function for processing message and send to destination kafka topic
    // Consumer for consuming message from kafka

    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<Message<Object>> processDebeziumOracle() {
        return message -> {
            try {
                Object payload = message.getPayload();

                log.info("Raw payload type: {}", payload.getClass());

                String json = payload.toString();

                DebeziumEnvelope<RecordData> envelope =
                        objectMapper.readValue(
                                json,
                                new TypeReference<>() {}
                        );

//                log.info("Operation: {}", envelope.getOp());
                System.out.println("Operation: " + envelope.getOp());
                if (envelope.getAfter() != null) {
//                    log.info("RECID: {}", envelope.getAfter().getRECID());

                    XmlData xmlData = envelope.getAfter().getXMLDATA();
                    if (xmlData != null) {
//                        log.info("Name: {}", xmlData.getName());
//                        log.info("Role: {}", xmlData.getRole());
                        System.out.println("Name: " + xmlData.getName());
                        System.out.println("Role: " + xmlData.getRole());

                    }
                }

            } catch (Exception e) {
                log.error("Error processing message", e);
            }
        };
    }


//    @Bean
//    public Consumer<ConsumerRecord<String, String>> processDebeziumOracle(){
//        return record -> {
//            try {
//                // Get value as string
//                String value = record.value();
//
//                // Deserialize to DebeziumEnvelope
//                DebeziumEnvelope<RecordData> debeziumEnvelope = objectMapper.readValue(
//                       value,
//                        new TypeReference<DebeziumEnvelope<RecordData>>() {}
//                );
//                System.out.println("mapped:" + debeziumEnvelope);
//                System.out.println("mapped XML: " + debeziumEnvelope.getAfter().getRECID());
//                System.out.println("mapped XML: " + debeziumEnvelope.getBefore().getXMLDATA());
//            } catch (Exception e){
//                log.error("Error processing record: {}", record.value(), e);
//            }
//
//
//        };
//    }

//    @Bean
//    public Consumer<GenericRecord> processDebeziumOracle() {
//        return record -> {
//            if (record == null) {
//                System.out.println("Tombstone event, skipping...");
//                return;
//            }
//
//            // Debezium "after" payload
//            GenericRecord after = (GenericRecord) record.get("after");
//            if (after == null) {
//                System.out.println("Delete/tombstone event, skipping...");
//                return;
//            }
//
//            String recId = after.get("RECID") != null ? after.get("RECID").toString() : null;
//            String xmlData = after.get("XMLDATA") != null ? after.get("XMLDATA").toString() : null;
//
//            System.out.println("RECID: " + recId);
//
//            if (xmlData == null || xmlData.isBlank()) {
//                System.out.println("XMLDATA is null, skipping parsing");
//                return;
//            }
//
//            try {
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
//
//                String name = doc.getElementsByTagName("name").item(0).getTextContent();
//                String role = doc.getElementsByTagName("role").item(0).getTextContent();
//
//                System.out.println("Parsed Name: " + name + ", Role: " + role);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//    }





    private Product mapToProduct(GenericRecord after) {
        Product product = new Product();

        // Map 'code' field
        product.setCode(
                after.get("code") != null
                        ? after.get("code").toString()
                        : null
        );

        // Map 'qty' field
        product.setQty(
                after.get("qty") != null
                        ? (Integer) after.get("qty")
                        : null
        );

        return product;
    }

    @Bean
    public Consumer<GenericRecord> processDebeziumProduct() {
        return record -> {

            // CDC operation: c, u, d, r
            String op = record.get("op").toString();

            // Debezium AFTER
            GenericRecord after = (GenericRecord) record.get("after");

            if (after == null) {
                System.out.println("Delete / tombstone event, op=" + op);
                return;
            }

            // ✅ Map GenericRecord → POJO
            Product product = mapToProduct(after);

            System.out.println("==== DEBEZIUM → POJO ====");
            System.out.println("Operation: " + op);
            System.out.println("Product Code: " + product.getCode());
            System.out.println("Product Qty: " + product.getQty());
        };
    }

    @Bean
    public Function<Product, Product> processProductDetail(){
        return product -> {
            System.out.println("Old product:" + product.getCode());
            System.out.println("Old product: " + product.getQty());

            // Processing
            product.setCode("ISTAD-"+product.getCode());

            // Producing
            return product;
        };
    }
    @Bean
    public Consumer<Product> processProduct() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());
        };
    }

    // A simple processor: Takes a string, makes it uppercase, and sends it on
    @Bean
    public Consumer<String> processMessage() {
        return input -> {
            System.out.println("Processing: " + input);
        };
    }

}

