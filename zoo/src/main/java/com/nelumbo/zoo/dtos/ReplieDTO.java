package com.nelumbo.zoo.dtos;

import jakarta.persistence.*;
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

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String body;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String author;
    private Date date;

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private Long commentId;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}
