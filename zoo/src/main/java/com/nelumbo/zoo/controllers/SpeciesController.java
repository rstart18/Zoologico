package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.SpeciesAnimalCountDTO;
import com.nelumbo.zoo.dtos.SpeciesDTO;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.services.SpeciesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/species")
public class SpeciesController {
    @Autowired
    SpeciesService speciesService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ArrayList<SpeciesDTO> getSpecies() { return speciesService.getSpecies(); }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public SpeciesDTO getSpeciesById(@PathVariable("id") Long id) { return speciesService.getSpeciesById(id); }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public SpeciesDTO saveASpecies(@RequestBody @Valid SpeciesDTO species) { return this.speciesService.saveASpecies(species); }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SpeciesDTO updateSpecies(@PathVariable("id") Long id, @RequestBody @Valid SpeciesDTO updatedSpecies) {
        return speciesService.updateSpecies(id, updatedSpecies);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpeciesById(@PathVariable("id") Long id) { this.speciesService.deleteSpecies(id); }

    @GetMapping("/animal-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SpeciesAnimalCountDTO>> getAnimalCountBySpecies() {
        Map<SpeciesEntity, Long> animalCounts = speciesService.countAnimalsBySpecies();

        List<SpeciesAnimalCountDTO> responseDTOs = animalCounts.entrySet().stream()
                .map(entry -> {
                    SpeciesAnimalCountDTO dto = new SpeciesAnimalCountDTO();
                    dto.setSpeciesName(entry.getKey().getName());
                    dto.setAnimalCount(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }
}
