package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.dtos.PercentRepliedDTO;
import com.nelumbo.zoo.entities.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentDTO> getComments();

    List<CommentDTO> getCommentsByAnimalId(Long animalId);

    CommentDTO saveComment (CommentDTO commentDTO);

    CommentDTO updateComment(Long commentId, CommentDTO updatedComment);

    void deleteComment(Long commentId);

    List<CommentDTO> searchComments(String query);

    List<CommentEntity> getCommentsWithReplies();

    PercentRepliedDTO calculatePercentageCommentsWithReplies();
}
