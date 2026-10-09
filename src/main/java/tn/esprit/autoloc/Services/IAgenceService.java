package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;

public interface IAgenceService {

    Agence ajouter(Agence agence);

    List<Agence> recupererTous();

    Agence recupererParId(Long id);

    Agence modifier(Long id, Agence agence);

    void supprimer(Long id);
}
