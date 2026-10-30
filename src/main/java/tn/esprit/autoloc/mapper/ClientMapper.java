package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setDateInscription(request.dateInscription());
        updateEntity(request, client);
        return client;
    }

    /** Recopie les attributs modifiables (ni l'identifiant, ni la date d'inscription). */
    public void updateEntity(ClientRequest request, Client client) {
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
    }

    public ClientResponse toResponse(Client client) {
        return new ClientResponse(client.getIdClient(), client.getNom(), client.getPrenom(), client.getEmail(),
                client.getTelephone(), client.getNumPermis(), client.getDateInscription());
    }
}
