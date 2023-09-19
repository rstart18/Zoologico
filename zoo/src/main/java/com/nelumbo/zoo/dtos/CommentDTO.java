package com.nelumbo.zoo.dtos;

import com.nelumbo.zoo.entities.ReplieEntity;
import jakarta.persistence.*;
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

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String body;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String author;
    private Date date;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private Long animalId;

    private List<ReplieEntity> replies;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}
