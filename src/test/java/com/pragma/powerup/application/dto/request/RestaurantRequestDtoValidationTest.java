package com.pragma.powerup.application.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private RestaurantRequestDto buildValidDto() {
        RestaurantRequestDto dto = new RestaurantRequestDto();
        dto.setId(1L);
        dto.setName("Restaurante El Sabor");
        dto.setAddress("Calle 123");
        dto.setOwnerId(5L);
        dto.setPhoneNumber("+573001112233");
        dto.setLogoUrl("https://logo.com/image.png");
        dto.setNit("900123456");
        return dto;
    }

    private void assertHasViolation(RestaurantRequestDto dto, String expectedMessageFragment) {
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(dto);
        assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().toLowerCase().contains(expectedMessageFragment.toLowerCase())),
                "Expected violation containing: " + expectedMessageFragment
        );
    }

    @Test
    void validDto_ShouldPassValidation() {
        RestaurantRequestDto dto = buildValidDto();
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nameWithOnlyNumbers_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setName("123456");
        assertHasViolation(dto, "");
    }

    @Test
    void emptyName_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setName(" ");
        assertHasViolation(dto, "");
    }

    @Test
    void phoneWithMoreThan13Chars_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setPhoneNumber("+57300111223345");
        assertHasViolation(dto, "");
    }

    @Test
    void phoneWithLetters_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setPhoneNumber("+57300abc123");
        assertHasViolation(dto, "");
    }

    @Test
    void phoneWithoutPlus_ShouldPassValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setPhoneNumber("3001112233");
        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nitWithLetters_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setNit("ABC123");
        assertHasViolation(dto, "");
    }

    @Test
    void emptyNit_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setNit("");
        assertHasViolation(dto, "");
    }

    @Test
    void emptyPhone_ShouldFailValidation() {
        RestaurantRequestDto dto = buildValidDto();
        dto.setPhoneNumber("");
        assertHasViolation(dto, "");
    }
}
