package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

public interface IVehiculeService {

    VehiculeResponse create(VehiculeRequest request);

    VehiculeResponse findById(Long id); // ResourceNotFoundException si absent

    List<VehiculeResponse> findAll();

    VehiculeResponse update(Long id, VehiculeRequest request);

    void deleteById(Long id); // ResourceNotFoundException si absent
}
