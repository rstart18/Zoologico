package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.ZoneDTO;
import com.nelumbo.zoo.entities.ZoneEntity;
import com.nelumbo.zoo.repositories.ZoneRepository;
import com.nelumbo.zoo.services.AnimalService;
import com.nelumbo.zoo.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {
    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    AnimalService animalService;

    public ArrayList<ZoneDTO> getZones() {
        List<ZoneEntity> zoneEntities = (List<ZoneEntity>) zoneRepository.findAll();
        List<ZoneDTO> zoneDTOs = zoneEntities.stream()
                .map(zoneEntity ->
                        ZoneDTO.builder()
                                .id(zoneEntity.getId())
                                .name(zoneEntity.getName())
                                .zones(zoneEntity.getZones())
                                .animals(zoneEntity.getAnimals())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<ZoneDTO>) zoneDTOs;
    }

    public ZoneDTO saveZone (ZoneDTO zoneDTO) {
        ZoneEntity zoneEntity = ZoneEntity
                .builder()
                .id(zoneDTO.getId())
                .name(zoneDTO.getName())
                .zones(zoneDTO.getZones())
                .animals(zoneDTO.getAnimals())
                .build();
        zoneRepository.save(zoneEntity);
        zoneDTO.setId(zoneEntity.getId());
        return zoneDTO;
    }

    public String deleteZone (Long id) {
        try {
            ZoneEntity zoneToDelete = zoneRepository.findById(id).orElse(null);

            if (zoneToDelete == null) {
                return "No existe la zona.";
            }

            if (zoneToDelete.getAnimals() != null && !zoneToDelete.getAnimals().isEmpty()) {
                return "La zona contiene animales.";
            }

            zoneRepository.deleteById(id);
            return "";
        } catch (Exception err) {
            return err.toString();
        }
    }

    public Map<ZoneEntity, Long> countAnimalsByZone() {
        List<ZoneEntity> zones = (List<ZoneEntity>) zoneRepository.findAll();

        return zones.stream()
                .collect(Collectors.toMap(
                        zone -> zone,
                        zone -> this.animalService.countAnimalsInZone(zone.getId())
                ));
    }

    public void createZone() {
        ZoneEntity zone1 = ZoneEntity
                .builder()
                .id(1L)
                .name("Reptiles")
                .build();
        zoneRepository.save(zone1);
    }
}
