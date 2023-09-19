package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.CommentDTO;
import com.nelumbo.zoo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public CommentDTO saveComment(@RequestBody CommentDTO comment) { return this.commentService.saveComment(comment); }

    @GetMapping("/percentage-with-replies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getPercentageCommentsWithReplies() {
        double percentage = commentService.calculatePercentageCommentsWithReplies();
        return ResponseEntity.ok(percentage);
    }
}
