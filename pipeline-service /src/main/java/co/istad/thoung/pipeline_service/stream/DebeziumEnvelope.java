package co.istad.thoung.pipeline_service.stream;

import lombok.Data;

@Data
public class DebeziumEnvelope<T> {
    private T before;
    private T after;
    private String op;
}

