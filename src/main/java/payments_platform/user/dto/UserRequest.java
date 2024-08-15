package payments_platform.user.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import payments_platform.user.enums.UserType;

public record UserRequest(

    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @NotBlank
    @CPF
    String document,

    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotNull
    BigDecimal balance,

    @NotNull
    UserType userType
) {}