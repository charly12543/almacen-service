package com.charlyCorporation.SecurityRoles.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO(String username,
                              String message,
                              String jwt,
                              boolean status) {
}
