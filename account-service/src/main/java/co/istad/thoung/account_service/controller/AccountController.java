package co.istad.thoung.account_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @GetMapping
    public String unsecuredEndpoint() {
        return "Account - unsecured endpoint";
    }
}
