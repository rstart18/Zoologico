package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.dtos.CustomAnimalDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public interface AnimalService {
    Long countAnimalsBySpecies(long zoneId);

    boolean areAnimalsInZone(Long zoneId);

    Long countAnimalsInZone(Long zoneId);

    boolean areAnimalsAssociatedWithSpecies(Long speciesId);

    ArrayList<AnimalDTO> getAnimals();

    AnimalDTO getAnimalById(Long id);

    List<CustomAnimalDTO> getAnimalsByRegistrationDate(LocalDate registrationDate);

    AnimalDTO saveAnimal (AnimalDTO animalDTO);

    AnimalDTO updateAnimal(Long id, AnimalDTO updatedAnimal);

    void deleteAnimal (Long id);

    void createAnimals();
}
