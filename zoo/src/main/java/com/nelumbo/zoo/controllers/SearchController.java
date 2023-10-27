package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.SearchResultsDTO;
import com.nelumbo.zoo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private SpeciesService speciesService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplieService replieService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public SearchResultsDTO search(@RequestParam String query) {
        return SearchResultsDTO.builder()
                .zones(zoneService.searchZones(query))
                .species(speciesService.searchSpecies(query))
                .animals(animalService.searchAnimals(query))
                .comments(commentService.searchComments(query))
                .replies(replieService.searchReplies(query))
                .build();
    }
}
