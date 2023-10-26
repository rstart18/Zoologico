package com.nelumbo.zoo.services;

import com.nelumbo.zoo.dtos.ReplieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplieService {
    List<ReplieDTO> getReplies();

    List<ReplieDTO> getRepliesByCommentId(Long commentId);

    ReplieDTO saveReplie (ReplieDTO replieDTO);

    ReplieDTO updateReplie(Long replieId, ReplieDTO updatedReplie);

    void deleteReplie(Long replieId);

}
