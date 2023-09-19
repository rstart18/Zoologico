package com.nelumbo.zoo.dtos;

import com.nelumbo.zoo.entities.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnimalDTO {
    private Long id;

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String name;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private Long speciesId;
    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private Long zoneId;

    private List<CommentEntity> comments;
}
