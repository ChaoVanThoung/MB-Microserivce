package co.istad.thoung.account_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/test")
public class UnSecurityController {

//    @Value("${service.name}") String name;
//    @Value("${secret.weak-password}") String weakPassword;
//    @Value("${secret.strong-password}") String strongPassword;

    @GetMapping
    public String unSecuredEndpoint() {
        return "unSecuredEndpoint";
    }

}
