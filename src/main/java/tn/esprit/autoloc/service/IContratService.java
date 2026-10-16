package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratService {

    Contrat create(Contrat contrat); // refuse un id non null

    Contrat findById(Long id); // ResourceNotFoundException si absent

    List<Contrat> findAll();

    Contrat update(Long id, Contrat contrat); // charge l'existant, recopie, save

    void deleteById(Long id); // ResourceNotFoundException si absent
}
