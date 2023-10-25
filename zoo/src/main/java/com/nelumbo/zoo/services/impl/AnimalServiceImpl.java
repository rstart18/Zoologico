package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.entities.ZoneEntity;
import com.nelumbo.zoo.repositories.AnimalRepository;
import com.nelumbo.zoo.repositories.SpeciesRepository;
import com.nelumbo.zoo.repositories.ZoneRepository;
import com.nelumbo.zoo.services.AnimalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    SpeciesRepository speciesRepository;

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

    public AnimalDTO getAnimalById(Long id) {
        Optional<AnimalEntity> animalEntityOptional = animalRepository.findById(id);

        if (animalEntityOptional.isPresent()) {
            AnimalEntity animalEntity = animalEntityOptional.get();

            return AnimalDTO.builder()
                    .id(animalEntity.getId())
                    .name(animalEntity.getName())
                    .speciesId(animalEntity.getSpeciesId())
                    .zoneId(animalEntity.getZoneId())
                    .comments(animalEntity.getComments())
                    .build();
        } else {
            throw new NoSuchElementException("Animal no encontrado.");
        }
    }

    public AnimalDTO saveAnimal(AnimalDTO animalDTO) {
        Optional<SpeciesEntity> speciesEntityOptional = speciesRepository.findById(animalDTO.getSpeciesId());
        if (!speciesEntityOptional.isPresent()) {
            throw new NoSuchElementException("Especie no encontrada.");
        }

        Optional<ZoneEntity> zoneEntityOptional = zoneRepository.findById(animalDTO.getZoneId());
        if (!zoneEntityOptional.isPresent()) {
            throw new NoSuchElementException("Zona no encontrada.");
        }

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

    @Transactional
    public AnimalDTO updateAnimal(Long id, AnimalDTO updatedAnimal) {
        Optional<AnimalEntity> animalEntityOptional = animalRepository.findById(id);

        if (animalEntityOptional.isPresent()) {
            AnimalEntity animalEntity = animalEntityOptional.get();

            Optional<SpeciesEntity> speciesEntityOptional = speciesRepository.findById(updatedAnimal.getSpeciesId());
            if (!speciesEntityOptional.isPresent()) {
                throw new NoSuchElementException("Especie no encontrada.");
            }

            Optional<ZoneEntity> zoneEntityOptional = zoneRepository.findById(updatedAnimal.getZoneId());
            if (!zoneEntityOptional.isPresent()) {
                throw new NoSuchElementException("Zona no encontrada.");
            }

            animalEntity.setName(updatedAnimal.getName());
            animalEntity.setSpeciesId(updatedAnimal.getSpeciesId());
            animalEntity.setZoneId(updatedAnimal.getZoneId());
            animalEntity.setComments(updatedAnimal.getComments());

            animalRepository.save(animalEntity);

            return AnimalDTO.builder()
                    .id(animalEntity.getId())
                    .name(animalEntity.getName())
                    .speciesId(animalEntity.getSpeciesId())
                    .zoneId(animalEntity.getZoneId())
                    .comments(animalEntity.getComments())
                    .build();
        } else {
            throw new NoSuchElementException("Animal no encontrado");
        }
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
