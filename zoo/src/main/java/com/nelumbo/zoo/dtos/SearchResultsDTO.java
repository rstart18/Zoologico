package com.nelumbo.zoo.dtos;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchResultsDTO {

    private List<ZoneDTO> zones;
    private List<SpeciesDTO> species;
    private List<AnimalDTO> animals;
    private List<CommentDTO> comments;
    private List<ReplieDTO> replies;

}
