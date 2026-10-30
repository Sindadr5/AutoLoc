package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

public interface IAgenceService {

    AgenceResponse create(AgenceRequest request);

    AgenceResponse findById(Long id); // ResourceNotFoundException si absent

    List<AgenceResponse> findAll();

    AgenceResponse update(Long id, AgenceRequest request);

    void deleteById(Long id); // ResourceNotFoundException si absent
}
