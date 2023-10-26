package com.nelumbo.zoo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nelumbo.zoo.entities.ReplieEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDTO {
    private Long id;

    @NotNull(message = "El campo body es requerido.")
    @NotBlank(message = "El campo body está vacío.")
    private String body;

    private String author;
    private Date date;

    @Min(value = 1, message = "El campo animalId debe ser mayor a cero.")
    private Long animalId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReplieEntity> replies;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}
