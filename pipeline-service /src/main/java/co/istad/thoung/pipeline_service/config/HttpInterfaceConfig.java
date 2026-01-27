package co.istad.thoung.pipeline_service.config;

import co.istad.thoung.pipeline_service.client.AccountClient;
import co.istad.thoung.pipeline_service.client.JsonPlaceHolderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    public JsonPlaceHolderClient jsonPlaceHolderClient(HttpInterfaceFactory httpInterfaceFactory){
        return httpInterfaceFactory.createClient("https://jsonplaceholder.typicode.com", JsonPlaceHolderClient.class);
    }

    @Bean
    public AccountClient accountClient(HttpInterfaceFactory httpInterfaceFactory){
        return httpInterfaceFactory.createClient("http://localhost:20260", AccountClient.class);
    }

}

