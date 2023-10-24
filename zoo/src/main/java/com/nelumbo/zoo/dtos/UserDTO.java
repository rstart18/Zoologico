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

    @NotNull(message = "El campo name es requerido.")
    @NotBlank(message = "El campo name se encuentra vacío.")
    private String name;
    @NotNull(message = "El campo email es requerido.")
    @NotBlank(message = "El campo email se encuentra vacío.")
    private String email;
    @NotNull(message = "El campo pass es requerido.")
    @NotBlank(message = "El campo pass se encuentra vacío.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pass;
    @NotNull(message = "El campo role es requerido.")
    @NotBlank(message = "El campo role se encuentra vacío.")
    private String role;
}
