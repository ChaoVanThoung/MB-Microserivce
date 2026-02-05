package co.istad.thoung.pipeline_service.config;

import co.istad.thoung.pipeline_service.client.AccountClient;
import co.istad.thoung.pipeline_service.client.JsonPlaceHolderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    public JsonPlaceHolderClient jsonPlaceHolderClient(HttpInterfaceFactory httpInterfaceFactory){
        return httpInterfaceFactory.createNormalClient("https://jsonplaceholder.typicode.com", JsonPlaceHolderClient.class);
    }

    @Bean
    public AccountClient accountClient(HttpInterfaceFactory httpInterfaceFactory){
        return httpInterfaceFactory.createLoadBalancedClient("http://account", AccountClient.class);
    }

}

