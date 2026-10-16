package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Paiement;

/** Lecture seule : la creation, la modification et la suppression passent par le Contrat. */
public interface IPaiementService {

    Paiement findById(Long id); // ResourceNotFoundException si absent

    List<Paiement> findAll();
}
