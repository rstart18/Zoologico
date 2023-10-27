package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.ZoneAnimalCountDTO;
import com.nelumbo.zoo.dtos.ZoneDTO;
import com.nelumbo.zoo.entities.ZoneEntity;
import com.nelumbo.zoo.repositories.ZoneRepository;
import com.nelumbo.zoo.services.AnimalService;
import com.nelumbo.zoo.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public ZoneDTO getZoneById(Long id) {
        List<ZoneEntity> zoneEntities = (List<ZoneEntity>) zoneRepository.findAll();
        Optional<ZoneEntity> zoneEntityOptional = zoneEntities.stream()
                .filter(zoneEntity -> zoneEntity.getId().equals(id))
                .findFirst();

        if (zoneEntityOptional.isPresent()) {
            ZoneEntity zoneEntity = zoneEntityOptional.get();
            return ZoneDTO.builder()
                    .id(zoneEntity.getId())
                    .name(zoneEntity.getName())
                    .zones(zoneEntity.getZones())
                    .animals(zoneEntity.getAnimals())
                    .build();
        } else {
            throw new NoSuchElementException("Zona no encontrada.");
        }
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

    public ZoneDTO updateZone(Long id, ZoneDTO updatedZone) {
        Optional<ZoneEntity> optionalZone = zoneRepository.findById(id);

        if (optionalZone.isPresent()) {
            ZoneEntity existingZone = optionalZone.get();

            existingZone.setName(updatedZone.getName());

            ZoneEntity updatedZoneEntity = zoneRepository.save(existingZone);

            return ZoneDTO.builder()
                    .id(updatedZoneEntity.getId())
                    .name(updatedZoneEntity.getName())
                    .build();
        } else {
            throw new NoSuchElementException("Zona no encontrada.");
        }
    }

    public void deleteZone(Long id) {
        ZoneEntity zoneToDelete = zoneRepository.findById(id).orElse(null);

        if (zoneToDelete == null) {
            throw new NoSuchElementException("Zona no encontrada.");
        }

        if (zoneToDelete.getAnimals() != null && !zoneToDelete.getAnimals().isEmpty()) {
            throw new IllegalStateException("La zona contiene animales.");
        }

        try {
            zoneRepository.deleteById(id);
        } catch (Exception err) {
            throw new RuntimeException("Error al eliminar la zona: " + err.toString());
        }
    }

    public List<ZoneDTO> searchZones(String query) {
        List<ZoneEntity> zoneEntities = zoneRepository.findByNameContaining(query);
        List<ZoneDTO> zoneDTOs = zoneEntities.stream()
                .map(zoneEntity ->
                        ZoneDTO.builder()
                                .id(zoneEntity.getId())
                                .name(zoneEntity.getName())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<ZoneDTO>) zoneDTOs;
    }

    public List<ZoneAnimalCountDTO> countAnimalsByZone() {
        List<ZoneEntity> zones = (List<ZoneEntity>) zoneRepository.findAll();
        List<ZoneAnimalCountDTO> animalCountDTOs = new ArrayList<>();
        Map<ZoneEntity, Long> animalCountByZone = zones.stream()
                .collect(Collectors.toMap(
                        zone -> zone,
                        zone -> this.animalService.countAnimalsInZone(zone.getId())
                ));

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

    public void createZone() {
        ZoneEntity zone1 = ZoneEntity
                .builder()
                .id(1L)
                .name("Reptiles")
                .build();
        zoneRepository.save(zone1);
    }
}
