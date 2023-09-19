package com.nelumbo.zoo.dtos;

import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.entities.SpeciesEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ZoneDTO {
    private Long id;

    @NotNull(message = "Campo requerido.")
    @NotBlank(message = "Campo vacío.")
    private String name;

    private List<SpeciesEntity> zones;
    private List<AnimalEntity> animals;
}
