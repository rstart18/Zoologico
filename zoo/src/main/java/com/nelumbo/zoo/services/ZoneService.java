package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.ZoneDTO;
import com.nelumbo.zoo.entities.ZoneEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface ZoneService {
    ArrayList<ZoneDTO> getZones();

    ZoneDTO getZoneById(Long id);

    ZoneDTO saveZone (ZoneDTO zoneDTO);

    ZoneDTO updateZone (Long id, ZoneDTO updatedZone);

    void deleteZone (Long id);

    Map<ZoneEntity, Long> countAnimalsByZone();

    void createZone();
}
