package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat ajouter(Contrat contrat);

    List<Contrat> recupererTous();

    Contrat recupererParId(Long id);

    Contrat modifier(Long id, Contrat contrat);

    void supprimer(Long id);
}
