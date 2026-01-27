package co.istad.thoung.pipeline_service.controller;

import co.istad.thoung.pipeline_service.client.AccountClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/client/account")
@RequiredArgsConstructor
public class AccountClientController {

    private final AccountClient accountClient;

    @GetMapping("/secured")
    public Map<String, Object> getSecuredData() {
        return  accountClient.getSecuredData();
    }
}
