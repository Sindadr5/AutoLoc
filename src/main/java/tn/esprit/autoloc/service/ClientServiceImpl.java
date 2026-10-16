package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        if (client.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        }
        valider(client);
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email deja utilise : " + client.getEmail());
        }
        if (client.getNumPermis() != null && clientRepository.existsByNumPermis(client.getNumPermis())) {
            throw new IllegalArgumentException("Numero de permis deja utilise : " + client.getNumPermis());
        }
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now(ZoneId.of("Africa/Tunis")));
        }
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {
        Client existant = findById(id);
        valider(client);
        if (!Objects.equals(existant.getEmail(), client.getEmail())
                && clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email deja utilise : " + client.getEmail());
        }
        if (client.getNumPermis() != null
                && !client.getNumPermis().equals(existant.getNumPermis())
                && clientRepository.existsByNumPermis(client.getNumPermis())) {
            throw new IllegalArgumentException("Numero de permis deja utilise : " + client.getNumPermis());
        }
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void valider(Client client) {
        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'e-mail du client est obligatoire");
        }
    }
}
