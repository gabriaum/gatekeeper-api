package com.gabriaum.gatekeeper.audit.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterEntranceDTO(
        @NotBlank String targetCPF
) {}