package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.DuplicateResourceException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponse create(ClientRequest request) {
        if (clientRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email deja utilise : " + request.email());
        }
        if (clientRepository.existsByNumPermis(request.numPermis())) {
            throw new DuplicateResourceException("Numero de permis deja utilise : " + request.numPermis());
        }
        Client client = clientMapper.toEntity(request);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now(ZoneId.of("Africa/Tunis")));
        }
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return clientMapper.toResponse(trouverOuErreur(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toResponse).toList();
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client existant = trouverOuErreur(id);
        if (!existant.getEmail().equals(request.email()) && clientRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email deja utilise : " + request.email());
        }
        if (!request.numPermis().equals(existant.getNumPermis()) && clientRepository.existsByNumPermis(request.numPermis())) {
            throw new DuplicateResourceException("Numero de permis deja utilise : " + request.numPermis());
        }
        clientMapper.updateEntity(request, existant);
        return clientMapper.toResponse(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client trouverOuErreur(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
