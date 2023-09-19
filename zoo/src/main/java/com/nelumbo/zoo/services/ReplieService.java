package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.ReplieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplieService {
    List<ReplieDTO> getComments();

    ReplieDTO saveResponse (ReplieDTO replieDTO);

}
