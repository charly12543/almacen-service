package com.charlyCorporation.SecurityRoles.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder
public record AuthLoginRequestDTO(@NotBlank String username,
                                  @NotBlank String password) {
}
