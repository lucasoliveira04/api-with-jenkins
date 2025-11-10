package com.jenkins.apiwithjenkins.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String cpf,
        String phone,
        LocalDate birthDate,
        AddressResponse address
) {
}


