package co.istad.thoung.customer_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @GetMapping
    public String unsecuredEndpoint() {
        return "Customer - unsecured endpoint";
    }

}
