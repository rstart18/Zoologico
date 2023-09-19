package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.repositories.AnimalRepository;
import com.nelumbo.zoo.repositories.ZoneRepository;
import com.nelumbo.zoo.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    ZoneRepository zoneRepository;

    public Long countAnimalsBySpecies(long zoneId) { return countAnimalsInZone(zoneId); }

    public boolean areAnimalsInZone(Long zoneId) {
        return animalRepository.existsByZoneId(zoneId);
    }

    public Long countAnimalsInZone(Long zoneId) {
        return animalRepository.countByZoneId(zoneId);
    }
    public boolean areAnimalsAssociatedWithSpecies(Long speciesId) {
        return animalRepository.existsBySpeciesId(speciesId);
    }
    public ArrayList<AnimalDTO> getAnimals() {
        List<AnimalEntity> animalEntities = (List<AnimalEntity>) animalRepository.findAll();
        List<AnimalDTO> animalDTOS = animalEntities.stream()
                .map(animalEntity ->
                        AnimalDTO.builder()
                                .id(animalEntity.getId())
                                .name(animalEntity.getName())
                                .speciesId(animalEntity.getSpeciesId())
                                .zoneId(animalEntity.getZoneId())
                                .comments(animalEntity.getComments())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<AnimalDTO>) animalDTOS;
    }

    public AnimalDTO saveAnimal (AnimalDTO animalDTO) {
        AnimalEntity animalEntity = AnimalEntity
                .builder()
                .id(animalDTO.getId())
                .name(animalDTO.getName())
                .speciesId(animalDTO.getSpeciesId())
                .zoneId(animalDTO.getZoneId())
                .comments(animalDTO.getComments())
                .build();
        animalRepository.save(animalEntity);
        animalDTO.setId(animalEntity.getId());
        return animalDTO;
    }

    public boolean deleteAnimal (Long id) {
        try {
            animalRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public void createAnimals() {
        AnimalEntity animal1 = AnimalEntity
                .builder()
                .id(1L)
                .name("Arto")
                .speciesId(1L)
                .zoneId(1L)
                .build();

        AnimalEntity animal2 = AnimalEntity
                .builder()
                .id(2L)
                .name("Ana")
                .speciesId(2L)
                .zoneId(1L)
                .build();

        animalRepository.save(animal1);
        animalRepository.save(animal2);
    }

}
