package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.ReplieDTO;
import com.nelumbo.zoo.services.ReplieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replie")
public class ReplieController {
    @Autowired
    ReplieService replieService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<ReplieDTO> getReplies() { return this.replieService.getReplies(); }

    @GetMapping(path = "/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<ReplieDTO> getRepliesByCommentId(@PathVariable("commentId") Long commentId) {
        return this.replieService.getRepliesByCommentId(commentId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ReplieDTO saveResponse(@RequestBody @Valid ReplieDTO replie) {
        return this.replieService.saveReplie(replie);
    }

    @PutMapping(path = "/{replieId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReplieDTO updateReplie(@PathVariable("replieId") Long replieId,
                                  @RequestBody @Valid ReplieDTO updatedReplie) {
        return this.replieService.updateReplie(replieId, updatedReplie);
    }

    @DeleteMapping(path = "/{replieId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReplieById(@PathVariable("replieId") Long replieId) {
        this.replieService.deleteReplie(replieId);
    }
}
