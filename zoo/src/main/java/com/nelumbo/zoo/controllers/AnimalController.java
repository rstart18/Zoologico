package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.dtos.CustomAnimalDTO;
import com.nelumbo.zoo.services.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    AnimalService animalService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ArrayList<AnimalDTO> getAnimals() {
        return animalService.getAnimals();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public AnimalDTO getAnimalById(@PathVariable("id") Long id) { return animalService.getAnimalById(id); }

    @GetMapping("/by-date/{registrationDate}")
    public List<CustomAnimalDTO> getAnimalsByRegistrationDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate registrationDate) {
        List<CustomAnimalDTO> customAnimalDTOs = animalService.getAnimalsByRegistrationDate(registrationDate);
        return customAnimalDTOs;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public AnimalDTO saveAnimal(@RequestBody @Valid AnimalDTO animal) { return this.animalService.saveAnimal(animal); }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AnimalDTO updateAnimal(@PathVariable("id") Long id, @RequestBody @Valid AnimalDTO updatedAnimal) {
        return animalService.updateAnimal(id, updatedAnimal);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimalById(@PathVariable("id") Long id) {
        this.animalService.deleteAnimal(id);
    }
}
