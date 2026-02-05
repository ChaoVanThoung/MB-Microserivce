package co.istad.thoung.pipeline_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
@RequiredArgsConstructor
public class HttpInterfaceFactory {

    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;
    private final WebClient.Builder loadBalancedWebClientBuilder;

    public <T> T createNormalClient(String basUrl, Class<T> interfaceClass) {
        WebClient webClient = WebClient.builder()
                .baseUrl(basUrl)
                .build();
        return createClient(webClient, interfaceClass);
    }

    public <T> T createLoadBalancedClient(String baseUrl, Class<T> interfaceClass) {

        var oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(auth2AuthorizedClientManager);
        oauth2.setDefaultClientRegistrationId("itp-standard");

        // without loadbalanced
//        WebClient webClient = WebClient.builder()
//                .baseUrl(baseUrl)
//                .apply(oauth2.oauth2Configuration())
//                .build();
//        return createClient(webClient, interfaceClass);

        // with loadbalanced
        WebClient webClient = loadBalancedWebClientBuilder
                .baseUrl(baseUrl)
                .apply(oauth2.oauth2Configuration())
                .build();
        return createClient(webClient, interfaceClass);
    }

    public <T> T createClient(WebClient webClient, Class<T> interfaceClass) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(interfaceClass);
    }
}

