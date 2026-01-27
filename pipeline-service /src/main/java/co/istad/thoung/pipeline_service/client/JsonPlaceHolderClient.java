package co.istad.thoung.pipeline_service.client;

import co.istad.thoung.pipeline_service.client.dto.UserResponse;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface JsonPlaceHolderClient {

    @GetExchange("/users")
    List<UserResponse> getAll();

}
