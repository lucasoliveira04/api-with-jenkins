package com.jenkins.apiwithjenkins.dto;

public record AddressResponse(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {
}
