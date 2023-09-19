package com.nelumbo.zoo.dtos;

import lombok.Data;

@Data
public class ZoneAnimalCountDTO {
    private Long zoneId;
    private String zoneName;
    private Long animalCount;
}