package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.entities.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentDTO> getComments();

    CommentDTO saveComment (CommentDTO commentDTO);

    List<CommentEntity> getCommentsWithReplies();

    double calculatePercentageCommentsWithReplies();
}
