package com.thoung.ebanking.security.controller;


import com.thoung.ebanking.security.dto.AuthenticationResponse;
import com.thoung.ebanking.security.dto.ProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

//    @GetMapping("/oauth2/authenticated/me")
//    public Mono<ProfileResponse> me(@AuthenticationPrincipal OAuth2User user) {
//
//        log.info("========== OAuth2User Attributes ==========");
//        if (user != null && user.getAttributes() != null) {
//            user.getAttributes().forEach((key, value) ->
//                    log.info("{} = {}", key, value)
//            );
//        } else {
//            log.error("OAuth2User or attributes is null!");
//        }
//        log.info("==========================================");
//
//        ProfileResponse profile = ProfileResponse.builder()
//                .username((user.getAttribute("preferred_username"))) // Changed
//                .email(user.getAttribute("email"))
//                .gender(user.getAttribute("gender"))
//                .familyName(user.getAttribute("family_name"))      // Changed (underscore)
//                .givenName(user.getAttribute("given_name"))        // Changed (underscore)
//                .phoneNumber(user.getAttribute("phone_number"))
//                .build();
//
//
//
//
//        log.info("Built profile: {}", profile);
//        System.out.println("profile: " + profile);
//
//        return Mono.just(profile);
//    }

    @GetMapping("/oauth2/authenticated/me")
    public Mono<ProfileResponse> me(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return Mono.error(new RuntimeException("User not authenticated"));
        }

        // Helper to avoid "null" strings
        java.util.function.Function<String, String> getAttr = (key) -> {
            Object attr = user.getAttribute(key);
            return attr != null ? String.valueOf(attr) : null;
        };

        ProfileResponse profile = ProfileResponse.builder()
                .username(getAttr.apply("preferred_username"))
                .email(getAttr.apply("email"))
                .gender(getAttr.apply("gender"))
                .familyName(getAttr.apply("family_name"))
                .givenName(getAttr.apply("given_name"))
                .phoneNumber(getAttr.apply("phone_number"))
                .dob(user.getAttribute("birthdate"))
                .build();


        log.info("========== OAuth2User Attributes ==========");
        if (user.getAttributes() != null) {
            user.getAttributes().forEach((key, value) -> {
                log.info(key + ": " + value);
            });
        }
        log.info("============================================");

        return Mono.just(profile);
    }

    @GetMapping("/is-authenticated")
    public AuthenticationResponse isAuthenticated(Authentication authentication) {

        return AuthenticationResponse.builder()
                .isAuthenticated(authentication != null)
                .build();
    }
}
