package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.ZoneAnimalCountDTO;
import com.nelumbo.zoo.dtos.ZoneDTO;
import com.nelumbo.zoo.entities.ZoneEntity;
import com.nelumbo.zoo.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zone")
public class ZoneController {
    @Autowired
    ZoneService zoneService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ArrayList<ZoneDTO> getZones() { return zoneService.getZones(); }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ZoneDTO saveZone(@RequestBody ZoneDTO zone) { return this.zoneService.saveZone(zone); }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteZoneById(@PathVariable("id") Long id) {
        String ok = this.zoneService.deleteZone(id);
        if (ok.isEmpty()) {
            return "Se ha eliminado la zona satisfactoriamente.";
        } else {
            return "No se ha podido eliminar la zona: " + ok;
        }
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
