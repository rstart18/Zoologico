package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.SpeciesDTO;
import com.nelumbo.zoo.entities.SpeciesEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface SpeciesService {
    ArrayList<SpeciesDTO> getSpecies();

    SpeciesDTO getSpeciesById(Long id);

    SpeciesDTO saveASpecies (SpeciesDTO speciesDTO);

    SpeciesDTO updateSpecies(Long id, SpeciesDTO updatedSpecies);

    void deleteSpecies(Long id);

    Map<SpeciesEntity, Long> countAnimalsBySpecies();

    void createSpecies();
}
