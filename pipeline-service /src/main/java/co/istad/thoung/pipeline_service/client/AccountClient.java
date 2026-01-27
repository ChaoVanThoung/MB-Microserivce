package co.istad.thoung.pipeline_service.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange("/api/v1/accounts")
public interface AccountClient {

    @GetExchange
    Map<String, Object> getSecuredData();

}
