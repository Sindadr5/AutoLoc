package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

public interface IClientService {

    ClientResponse create(ClientRequest request);

    ClientResponse findById(Long id); // ResourceNotFoundException si absent

    List<ClientResponse> findAll();

    ClientResponse update(Long id, ClientRequest request);

    void deleteById(Long id); // ResourceNotFoundException si absent
}
