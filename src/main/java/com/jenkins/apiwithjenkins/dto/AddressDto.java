package com.jenkins.apiwithjenkins.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jenkins.apiwithjenkins.entity.Address;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressDto(
        @NotNull String street,
        @NotNull String city,
        @NotNull String state,
        @NotNull String zipCode,
        @NotNull String country) implements Serializable {
}