package com.nelumbo.zoo.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class CustomAnimalDTO {
    private String animal;
    private String zone;
    private String species;
}