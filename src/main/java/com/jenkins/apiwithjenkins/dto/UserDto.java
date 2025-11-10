package com.jenkins.apiwithjenkins.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.jenkins.apiwithjenkins.entity.User}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(
        @NotNull String name,
        @NotNull String email,
        @NotNull String cpf,
        String phone,
        @NotNull LocalDate birthDate,
        List<AddressDto> addresses
) implements Serializable { }