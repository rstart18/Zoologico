package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.SpeciesAnimalCountDTO;
import com.nelumbo.zoo.dtos.SpeciesDTO;
import com.nelumbo.zoo.entities.SpeciesEntity;
import com.nelumbo.zoo.services.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public SpeciesDTO saveASpecies(@RequestBody SpeciesDTO species) { return this.speciesService.saveASpecies(species); }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteSpeciesById(@PathVariable("id") Long id) {
        String ok = this.speciesService.deleteSpecies(id);
        if (ok.isEmpty()) {
            return "Se ha eliminado la especie satisfactoriamente.";
        } else {
            return "No se ha podido eliminar la especie: " + ok;
        }
    }

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
