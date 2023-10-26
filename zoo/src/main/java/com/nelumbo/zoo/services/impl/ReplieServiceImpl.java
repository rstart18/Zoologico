package com.nelumbo.zoo.services.impl;

import com.nelumbo.zoo.dtos.ReplieDTO;
import com.nelumbo.zoo.entities.CommentEntity;
import com.nelumbo.zoo.entities.ReplieEntity;
import com.nelumbo.zoo.repositories.CommentRepository;
import com.nelumbo.zoo.repositories.ReplieRepository;
import com.nelumbo.zoo.services.ReplieService;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ReplieServiceImpl implements ReplieService {
    @Autowired
    ReplieRepository replieRepository;

    @Autowired
    CommentRepository commentRepository;

    public List<ReplieDTO> getReplies() {
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

    public List<ReplieDTO> getRepliesByCommentId(Long commentId) {
        List<ReplieEntity> allReplies = (List<ReplieEntity>) replieRepository.findAll();

        List<ReplieDTO> repliesForComment = allReplies.stream()
                .filter(replieEntity -> replieEntity.getCommentId().equals(commentId))
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

        return repliesForComment;
    }

    public ReplieDTO saveReplie(ReplieDTO replieDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            replieDTO.setAuthor(email);
        }

        if (replieDTO.getCommentId() == null) {
            throw new IllegalArgumentException("El campo commentId es requerido.");
        }

        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(replieDTO.getCommentId());

        if (commentEntityOptional.isPresent()) {
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
        } else {
            throw new NoSuchElementException("Comentario no encontrado.");
        }
    }

    @Transactional
    public ReplieDTO updateReplie(Long replieId, ReplieDTO updatedReplie) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();

        Optional<ReplieEntity> replieEntityOptional = replieRepository.findById(replieId);

        if (replieEntityOptional.isPresent()) {
            ReplieEntity replieEntity = replieEntityOptional.get();

            if (!replieEntity.getAuthor().equals(email)) {
                throw new AccessDeniedException("No tienes permiso para editar esta respuesta.");
            }

            replieEntity.setBody(updatedReplie.getBody());

            replieRepository.save(replieEntity);

            ReplieDTO updatedReplieDTO = ReplieDTO.builder()
                    .id(replieEntity.getId())
                    .body(replieEntity.getBody())
                    .author(replieEntity.getAuthor())
                    .date(replieEntity.getDate())
                    .commentId(replieEntity.getCommentId())
                    .build();

            return updatedReplieDTO;
        } else {
            throw new NoSuchElementException("Respuesta no encontrada.");
        }
    }

    @Transactional
    public void deleteReplie(Long replieId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String currentUserEmail = userDetails.getUsername();

            Optional<ReplieEntity> replieEntityOptional = replieRepository.findById(replieId);

            if (replieEntityOptional.isPresent()) {
                ReplieEntity replieEntity = replieEntityOptional.get();

                if (replieEntity.getAuthor().equals(currentUserEmail)) {
                    replieRepository.deleteById(replieId);
                } else {
                    throw new AccessDeniedException("No tienes permiso para eliminar esta respuesta.");
                }
            } else {
                throw new NoSuchElementException("Respuesta no encontrada.");
            }
        }
    }

}
