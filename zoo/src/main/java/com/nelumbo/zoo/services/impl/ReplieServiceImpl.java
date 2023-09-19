package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.ReplieDTO;
import com.nelumbo.zoo.entities.ReplieEntity;
import com.nelumbo.zoo.repositories.ReplieRepository;
import com.nelumbo.zoo.services.ReplieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplieServiceImpl implements ReplieService {
    @Autowired
    ReplieRepository replieRepository;

    public List<ReplieDTO> getComments() {
        List<ReplieEntity> replieEntities = (List<ReplieEntity>) replieRepository.findAll();
        List<ReplieDTO> replieDTOS = replieEntities.stream()
                .map(replieEntity ->
                        ReplieDTO.builder()
                                .id(replieEntity.getId())
                                .body(replieEntity.getBody())
                                .author(replieEntity.getAuthor())
                                .date(replieEntity.getDate())
                                .commentId(replieEntity.getCommentId())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<ReplieDTO>) replieDTOS;
    }
    public ReplieDTO saveResponse (ReplieDTO replieDTO) {
        ReplieEntity replieEntity = ReplieEntity
                .builder()
                .id(replieDTO.getId())
                .body(replieDTO.getBody())
                .author(replieDTO.getAuthor())
                .date(replieDTO.getDate())
                .commentId(replieDTO.getCommentId())
                .build();
        replieRepository.save(replieEntity);
        replieDTO.setId(replieEntity.getId());
        replieDTO.setDate(replieEntity.getDate());
        return replieDTO;
    }
}
