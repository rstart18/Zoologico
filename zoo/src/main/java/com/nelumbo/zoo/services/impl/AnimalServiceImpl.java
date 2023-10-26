package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.dtos.CustomAnimalDTO;
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

import java.time.*;
import java.util.*;
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

    public List<CustomAnimalDTO> getAnimalsByRegistrationDate(LocalDate registrationDate) {

        LocalDateTime startOfDay = registrationDate.atStartOfDay();
        LocalDateTime endOfDay = registrationDate.atTime(LocalTime.MAX);

        List<AnimalEntity> animalEntities = animalRepository.findByDateBetween(startOfDay, endOfDay);

        List<CustomAnimalDTO> customAnimalDTOs = new ArrayList<>();

        for (AnimalEntity animalEntity : animalEntities) {
            SpeciesEntity speciesEntity = speciesRepository.findById(animalEntity.getSpeciesId()).orElse(
                    new SpeciesEntity().builder().id(-1L).name(null).build());
            ZoneEntity zoneEntity = zoneRepository.findById(animalEntity.getZoneId()).orElse(
                    new ZoneEntity().builder().id(-1L).name(null).build());

            CustomAnimalDTO customAnimalDTO = CustomAnimalDTO.builder()
                    .animal(animalEntity.getName())
                    .species(speciesEntity.getName())
                    .zone(zoneEntity.getName())
                    .build();

            customAnimalDTOs.add(customAnimalDTO);
        }

        return customAnimalDTOs;
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
            throw new NoSuchElementException("Animal no encontrado.");
        }
    }

    public void deleteAnimal (Long id) {
        Optional<AnimalEntity> animalEntityOptional = animalRepository.findById(id);

        if (animalEntityOptional.isPresent()) {
            animalRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Animal no encontrado.");
        }
    }

    public void createAnimals() {
        AnimalEntity animal1 = AnimalEntity
                .builder()
                .id(1L)
                .name("Arto")
                .speciesId(1L)
                .zoneId(1L)
                .date(LocalDateTime.now())
                .build();

        AnimalEntity animal2 = AnimalEntity
                .builder()
                .id(2L)
                .name("Ana")
                .speciesId(2L)
                .zoneId(1L)
                .date(LocalDateTime.now())
                .build();

        animalRepository.save(animal1);
        animalRepository.save(animal2);
    }

}
