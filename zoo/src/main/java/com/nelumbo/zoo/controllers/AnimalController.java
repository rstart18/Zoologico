package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.AnimalDTO;
import com.nelumbo.zoo.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public AnimalDTO saveAnimal(@RequestBody AnimalDTO animal) { return this.animalService.saveAnimal(animal); }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAnimalById(@PathVariable("id") Long id) {
        boolean ok = this.animalService.deleteAnimal(id);
        if (ok) {
            return "Se ha eliminado el animal satisfactoriamente.";
        } else {
            return "No se ha podido eliminar el animal.";
        }
    }
}
