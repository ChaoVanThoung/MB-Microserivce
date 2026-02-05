package co.istad.thoung.pipeline_service.controller;

import co.istad.thoung.pipeline_service.client.JsonPlaceHolderClient;
import co.istad.thoung.pipeline_service.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/jph")
@RequiredArgsConstructor
public class JsonPlaceHolderController {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;

    @GetMapping("/users")
    List<UserResponse> getUser(){
        return jsonPlaceHolderClient.getAll();
    }
}
