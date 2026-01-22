package co.istad.thoung.account_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @GetMapping
    public Map<String, Object> unsecuredEndpoint() {
        return Map.of("data",
                "Account - Secured endpoint");
    }

}
