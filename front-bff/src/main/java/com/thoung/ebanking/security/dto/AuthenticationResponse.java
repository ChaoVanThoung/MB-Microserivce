package com.thoung.ebanking.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    Boolean isAuthenticated
) {
}
