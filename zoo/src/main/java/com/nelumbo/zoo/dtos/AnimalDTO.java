package com.nelumbo.zoo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nelumbo.zoo.entities.CommentEntity;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.services.SpeciesService;
import com.nelumbo.zoo.services.ZoneService;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnimalDTO {

    private Long id;

    @NotNull(message = "El campo name es requerido.")
    @NotBlank(message = "El campo name está vacío.")
    private String name;
    @NotNull(message = "El campo speciesId es requerido.")
    @Min(value = 1, message = "El campo speciesId debe ser mayor a cero.")
    private Long speciesId;
    @NotNull(message = "El campo zoneId es requerido.")
    @Min(value = 1, message = "El campo zoneId debe ser mayor a cero.")
    private Long zoneId;

    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentEntity> comments;
}
