package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface IClientService {

    Client ajouter(Client client);

    List<Client> recupererTous();

    Client recupererParId(Long id);

    Client modifier(Long id, Client client);

    void supprimer(Long id);
}
