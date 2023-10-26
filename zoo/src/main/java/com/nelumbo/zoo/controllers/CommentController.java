package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<CommentDTO> getComments() {
        return this.commentService.getComments();
    }

    @GetMapping(path = "/{animalId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<CommentDTO> getCommentsByAnimalId(@PathVariable("animalId") Long animalId) {
        return this.commentService.getCommentsByAnimalId(animalId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public CommentDTO saveComment(@RequestBody @Valid CommentDTO comment) { return this.commentService.saveComment(comment); }

    @PutMapping(path = "/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public CommentDTO updateComment(@PathVariable("commentId") Long commentId,
                                    @RequestBody @Valid CommentDTO updatedComment) {
        return this.commentService.updateComment(commentId, updatedComment);
    }

    @DeleteMapping(path = "/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable("commentId") Long commentId) {
        this.commentService.deleteComment(commentId);
    }

    @GetMapping("/percentage-with-replies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getPercentageCommentsWithReplies() {
        double percentage = commentService.calculatePercentageCommentsWithReplies();
        return ResponseEntity.ok(percentage);
    }
}
