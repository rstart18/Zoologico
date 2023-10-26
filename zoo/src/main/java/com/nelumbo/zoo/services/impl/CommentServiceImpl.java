package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.entities.AnimalEntity;
import com.nelumbo.zoo.entities.CommentEntity;
import com.nelumbo.zoo.repositories.AnimalRepository;
import com.nelumbo.zoo.repositories.CommentRepository;
import com.nelumbo.zoo.services.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AnimalRepository animalRepository;

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

    public List<CommentDTO> getCommentsByAnimalId(Long animalId) {
        List<CommentEntity> commentEntities = commentRepository.findByAnimalId(animalId);
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
        return commentDTOS;
    }

    public CommentDTO saveComment(CommentDTO commentDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            commentDTO.setAuthor(email);
        }

        if (commentDTO.getAnimalId() == null) {
            throw new IllegalArgumentException("El campo AnimalId es requerido.");
        }

        Optional<AnimalEntity> animalEntityOptional = animalRepository.findById(commentDTO.getAnimalId());
        if (animalEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Animal no encontrado.");
        }

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

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO updatedComment) {
        CommentEntity originalComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comentario no encontrado"));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            if (!originalComment.getAuthor().equals(email)) {
                throw new AccessDeniedException("No tienes permiso para editar este comentario");
            }
        }

        originalComment.setBody(updatedComment.getBody());

        commentRepository.save(originalComment);

        return CommentDTO.builder()
                .id(originalComment.getId())
                .body(originalComment.getBody())
                .author(originalComment.getAuthor())
                .date(originalComment.getDate())
                .animalId(originalComment.getAnimalId())
                .replies(originalComment.getReplies())
                .build();
    }

    public List<CommentEntity> getCommentsWithReplies() { return commentRepository.findByRepliesNotNull(); }

    @Transactional
    public void deleteComment(Long commentId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String currentUserEmail = userDetails.getUsername();

            Optional<CommentEntity> commentEntityOptional = commentRepository.findById(commentId);

            if (commentEntityOptional.isPresent()) {
                CommentEntity commentEntity = commentEntityOptional.get();

                if (commentEntity.getAuthor().equals(currentUserEmail)) {
                    commentRepository.deleteById(commentId);
                } else {
                    throw new AccessDeniedException("No tienes permiso para eliminar este comentario.");
                }
            } else {
                throw new NoSuchElementException("Comentario no encontrado.");
            }
        }
    }

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
