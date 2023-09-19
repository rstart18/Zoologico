package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.entities.CommentEntity;
import com.nelumbo.zoo.repositories.CommentRepository;
import com.nelumbo.zoo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<CommentDTO> getComments() {
        List<CommentEntity> commentEntities = (List<CommentEntity>) commentRepository.findAll();
        List<CommentDTO> commentDTOS = commentEntities.stream()
                .map(commentEntity ->
                        CommentDTO.builder()
                                .id(commentEntity.getId())
                                .body(commentEntity.getBody())
                                .author(commentEntity.getAuthor())
                                .date(commentEntity.getDate())
                                .animalId(commentEntity.getAnimalId())
                                .replies(commentEntity.getReplies())
                                .build()
                )
                .collect(Collectors.toList());
        return (ArrayList<CommentDTO>) commentDTOS;
    }
    public CommentDTO saveComment (CommentDTO commentDTO) {
        CommentEntity commentEntity = CommentEntity
                .builder()
                .id(commentDTO.getId())
                .body(commentDTO.getBody())
                .author(commentDTO.getAuthor())
                .date(commentDTO.getDate())
                .animalId(commentDTO.getAnimalId())
                .replies(commentDTO.getReplies())
                .build();
        commentRepository.save(commentEntity);
        commentDTO.setId(commentEntity.getId());
        commentDTO.setDate(commentEntity.getDate());
        return commentDTO;
    }

    public List<CommentEntity> getCommentsWithReplies() { return commentRepository.findByRepliesNotNull(); }
    public double calculatePercentageCommentsWithReplies() {
        List<CommentEntity> commentsWithReplies = getCommentsWithReplies();
        List<CommentDTO> allComments = getComments();

        if (allComments.isEmpty()) {
            return 0.0;
        }

        double percentage = ((double) commentsWithReplies.size() / allComments.size()) * 100.0;

        return percentage;
    }
}
