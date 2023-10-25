package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.AnimalDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface AnimalService {
    Long countAnimalsBySpecies(long zoneId);

    boolean areAnimalsInZone(Long zoneId);

    Long countAnimalsInZone(Long zoneId);

    boolean areAnimalsAssociatedWithSpecies(Long speciesId);

    ArrayList<AnimalDTO> getAnimals();

    AnimalDTO getAnimalById(Long id);

    AnimalDTO saveAnimal (AnimalDTO animalDTO);

    AnimalDTO updateAnimal(Long id, AnimalDTO updatedAnimal);

    boolean deleteAnimal (Long id);

    void createAnimals();
}
