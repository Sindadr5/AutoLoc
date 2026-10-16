package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {

    Employe create(Employe employe); // refuse un id non null

    Employe findById(Long id); // ResourceNotFoundException si absent

    List<Employe> findAll();

    Employe update(Long id, Employe employe); // charge l'existant, recopie, save

    void deleteById(Long id); // ResourceNotFoundException si absent
}
