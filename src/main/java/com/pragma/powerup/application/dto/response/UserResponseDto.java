package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "El valor debe ser numérico")
    private String documentNumber;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Teléfono no válido")
    private String phone;

    @Past
    private LocalDate birthDate;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "La longitud debe ser de minimo 8 caracteres")
    private String password;

    private String role;

}
