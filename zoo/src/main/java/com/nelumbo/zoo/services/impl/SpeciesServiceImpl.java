package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.SpeciesAnimalCountDTO;
import com.nelumbo.zoo.dtos.SpeciesDTO;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.repositories.SpeciesRepository;
import com.nelumbo.zoo.repositories.ZoneRepository;
import com.nelumbo.zoo.services.AnimalService;
import com.nelumbo.zoo.services.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpeciesServiceImpl implements SpeciesService {
    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    AnimalService animalService;

    public ArrayList<SpeciesDTO> getSpecies() {
        List<SpeciesEntity> speciesEntities = (List<SpeciesEntity>) speciesRepository.findAll();
        List<SpeciesDTO> speciesDTOs = speciesEntities.stream()
                .map(speciesEntity ->
                        SpeciesDTO.builder()
                                .id(speciesEntity.getId())
                                .name(speciesEntity.getName())
                                .zoneId(speciesEntity.getZoneId())
                                .animals(speciesEntity.getAnimals())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<SpeciesDTO>) speciesDTOs;
    }

    public SpeciesDTO getSpeciesById(Long id) {
        Optional<SpeciesEntity> speciesEntityOptional = speciesRepository.findById(id);

        if (speciesEntityOptional.isPresent()) {
            SpeciesEntity speciesEntity = speciesEntityOptional.get();

            return SpeciesDTO.builder()
                    .id(speciesEntity.getId())
                    .name(speciesEntity.getName())
                    .zoneId(speciesEntity.getZoneId())
                    .animals(speciesEntity.getAnimals())
                    .build();
        } else {
            throw new NoSuchElementException("Especie no encontrada.");
        }
    }

    public SpeciesDTO saveASpecies (SpeciesDTO speciesDTO) {
        if (speciesRepository.existsByName(speciesDTO.getName())) {
            throw new IllegalArgumentException("Una especie ya esta registrada con ese nombre.");
        }
        if (!zoneRepository.existsById(speciesDTO.getZoneId())) {
            throw new IllegalArgumentException("El campo zoneId especificado no se ha encontrado.");
        }

        SpeciesEntity speciesEntity = SpeciesEntity
                .builder()
                .id(speciesDTO.getId())
                .name(speciesDTO.getName())
                .zoneId(speciesDTO.getZoneId())
                .animals(speciesDTO.getAnimals())
                .build();
        speciesRepository.save(speciesEntity);
        speciesDTO.setId(speciesEntity.getId());
        return speciesDTO;
    }

    public SpeciesDTO updateSpecies(Long id, SpeciesDTO updatedSpecies) {
        Optional<SpeciesEntity> existingSpeciesOptional = speciesRepository.findById(id);

        if (existingSpeciesOptional.isPresent()) {
            SpeciesEntity existingSpecies = existingSpeciesOptional.get();

            existingSpecies.setName(updatedSpecies.getName());
            existingSpecies.setZoneId(updatedSpecies.getZoneId());
            existingSpecies.setAnimals(updatedSpecies.getAnimals());

            speciesRepository.save(existingSpecies);

            return SpeciesDTO.builder()
                    .id(existingSpecies.getId())
                    .name(existingSpecies.getName())
                    .zoneId(existingSpecies.getZoneId())
                    .animals(existingSpecies.getAnimals())
                    .build();
        } else {
            throw new NoSuchElementException("Especie no encontrada.");
        }
    }

    public void deleteSpecies(Long id) {
        SpeciesEntity speciesToDelete = speciesRepository.findById(id).orElse(null);

        if (speciesToDelete == null) {
            throw new NoSuchElementException("Especie no encontrada.");
        }

        if (animalService.areAnimalsAssociatedWithSpecies(id)) {
            throw new IllegalStateException("La especie tiene animales asociados.");
        }
        try {
            speciesRepository.deleteById(id);
        } catch (Exception err) {
            throw new RuntimeException("Error al eliminar la especie: " + err.toString());
        }
    }

    public List<SpeciesDTO> searchSpecies(String query) {
        List<SpeciesEntity> speciesEntities = speciesRepository.findByNameContaining(query);
        List<SpeciesDTO> speciesDTOs = speciesEntities.stream()
                .map(speciesEntity ->
                        SpeciesDTO.builder()
                                .id(speciesEntity.getId())
                                .name(speciesEntity.getName())
                                .zoneId(speciesEntity.getZoneId())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<SpeciesDTO>) speciesDTOs;
    }

    public List<SpeciesAnimalCountDTO> countAnimalsBySpecies() {
        List<SpeciesEntity> speciesList = (List<SpeciesEntity>) speciesRepository.findAll();
        Map<SpeciesEntity, Long> animalCounts = new HashMap<>();

        for (SpeciesEntity species : speciesList) {
            Long count = animalService.countAnimalsBySpecies(species.getId());
            animalCounts.put(species, count);
        }

        List<SpeciesAnimalCountDTO> responseDTOs = animalCounts.entrySet().stream()
                .map(entry -> {
                    SpeciesAnimalCountDTO dto = new SpeciesAnimalCountDTO();
                    dto.setSpeciesName(entry.getKey().getName());
                    dto.setAnimalCount(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public void createSpecies() {
        SpeciesEntity species1 = SpeciesEntity
                .builder()
                .id(1L)
                .name("Lagarto")
                .zoneId(1L)
                .build();

        SpeciesEntity species2 = SpeciesEntity
                .builder()
                .id(2L)
                .name("Iguana")
                .zoneId(1L)
                .build();

        speciesRepository.save(species1);
        speciesRepository.save(species2);
    }
}
