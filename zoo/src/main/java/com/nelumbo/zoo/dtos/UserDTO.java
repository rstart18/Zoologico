package com.nelumbo.zoo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String name;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String email;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pass;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String role;
}
