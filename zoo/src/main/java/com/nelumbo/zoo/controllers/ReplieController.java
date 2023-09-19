package com.nelumbo.zoo.controllers;

import com.nelumbo.zoo.dtos.ReplieDTO;
import com.nelumbo.zoo.services.ReplieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/replie")
public class ReplieController {
    @Autowired
    ReplieService replieService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ReplieDTO saveResponse(@RequestBody ReplieDTO replie) { return this.replieService.saveResponse(replie); }

}
