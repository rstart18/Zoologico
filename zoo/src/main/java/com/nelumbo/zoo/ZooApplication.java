package com.nelumbo.zoo;

import com.nelumbo.zoo.services.AnimalService;
import com.nelumbo.zoo.services.SpeciesService;
import com.nelumbo.zoo.services.UserService;
import com.nelumbo.zoo.services.ZoneService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZooApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private SpeciesService speciesService;

	@Autowired
	private AnimalService animalService;

	@PostConstruct
	public void init() {
		userService.createUsers();
		zoneService.createZone();
		speciesService.createSpecies();
		animalService.createAnimals();
	}
}
