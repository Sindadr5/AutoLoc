package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    @Transactional
    public Client ajouter(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email deja utilise : " + client.getEmail());
        }
        if (clientRepository.existsByNumPermis(client.getNumPermis())) {
            throw new IllegalArgumentException("Numero de permis deja utilise : " + client.getNumPermis());
        }
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now(ZoneId.of("Africa/Tunis")));
        }
        client.setIdClient(null); // id null : INSERT
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> recupererTous() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Client modifier(Long id, Client client) {
        Client existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        if (!existant.getEmail().equals(client.getEmail()) && clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email deja utilise : " + client.getEmail());
        }
        if (!existant.getNumPermis().equals(client.getNumPermis()) && clientRepository.existsByNumPermis(client.getNumPermis())) {
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
    @Transactional
    public void supprimer(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable : id=" + id);
        }
        clientRepository.deleteById(id);
    }

    private Client trouverOuErreur(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable : id=" + id));
    }
}
