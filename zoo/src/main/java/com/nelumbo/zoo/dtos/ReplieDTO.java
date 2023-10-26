package com.nelumbo.zoo.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReplieDTO {
    private Long id;

    @NotNull(message = "El campo body es requerido.")
    @NotBlank(message = "El campo body está vacío.")
    private String body;

    private String author;
    private Date date;

    @Min(value = 1, message = "El campo animalId debe ser mayor a cero.")
    private Long commentId;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}
