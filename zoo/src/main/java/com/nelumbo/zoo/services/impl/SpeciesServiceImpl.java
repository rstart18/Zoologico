package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.SpeciesDTO;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.repositories.SpeciesRepository;
import com.nelumbo.zoo.services.AnimalService;
import com.nelumbo.zoo.services.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpeciesServiceImpl implements SpeciesService {
    @Autowired
    SpeciesRepository speciesRepository;

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

    public SpeciesDTO saveASpecies (SpeciesDTO speciesDTO) {
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

    public String deleteSpecies(Long id) {
        try {
            SpeciesEntity speciesToDelete = speciesRepository.findById(id).orElse(null);

            if (speciesToDelete == null) {
                return "No existe la especie.";
            }

            if (animalService.areAnimalsAssociatedWithSpecies(id)) {
                return "La especie tiene animales asociados.";
            }

            speciesRepository.deleteById(id);
            return "";
        } catch (Exception err) {
            return err.toString();
        }
    }

    public Map<SpeciesEntity, Long> countAnimalsBySpecies() {
        List<SpeciesEntity> speciesList = (List<SpeciesEntity>) speciesRepository.findAll();
        Map<SpeciesEntity, Long> animalCounts = new HashMap<>();

        for (SpeciesEntity species : speciesList) {
            Long count = animalService.countAnimalsBySpecies(species.getId());
            animalCounts.put(species, count);
        }

        return animalCounts;
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
