package com.nelumbo.zoo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelumbo.zoo.dtos.ZoneAnimalCountDTO;
import com.nelumbo.zoo.dtos.ZoneDTO;
import com.nelumbo.zoo.entities.ZoneEntity;
import com.nelumbo.zoo.services.ZoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zone")
public class ZoneController {

    private final ObjectMapper objectMapper;
    @Autowired
    ZoneService zoneService;

    @Autowired
    public ZoneController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ArrayList<ZoneDTO> getZones() { return zoneService.getZones(); }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ZoneDTO getZoneById(@PathVariable("id") Long id) { return zoneService.getZoneById(id); }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ZoneDTO saveZone(@RequestBody @Valid ZoneDTO zone) { return this.zoneService.saveZone(zone); }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ZoneDTO updateZone(@PathVariable("id") Long id, @RequestBody @Valid ZoneDTO updatedZone) {
        return zoneService.updateZone(id, updatedZone);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZoneById(@PathVariable("id") Long id) {
        this.zoneService.deleteZone(id);
    }

    @GetMapping("/animal-count")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ZoneAnimalCountDTO> getAnimalCountByZone() {
        List<ZoneAnimalCountDTO> animalCountDTOs = new ArrayList<>();

        Map<ZoneEntity, Long> animalCountByZone = zoneService.countAnimalsByZone();

        for (Map.Entry<ZoneEntity, Long> entry : animalCountByZone.entrySet()) {
            ZoneEntity zone = entry.getKey();
            Long animalCount = entry.getValue();

            ZoneAnimalCountDTO dto = new ZoneAnimalCountDTO();
            dto.setZoneId(zone.getId());
            dto.setZoneName(zone.getName());
            dto.setAnimalCount(animalCount);

            animalCountDTOs.add(dto);
        }

        return animalCountDTOs;
    }
}
