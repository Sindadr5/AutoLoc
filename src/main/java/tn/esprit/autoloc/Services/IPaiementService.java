package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement ajouter(Paiement paiement);

    List<Paiement> recupererTous();

    Paiement recupererParId(Long id);

    Paiement modifier(Long id, Paiement paiement);

    void supprimer(Long id);
}
