package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe ajouter(Employe employe);

    List<Employe> recupererTous();

    Employe recupererParId(Long id);

    Employe modifier(Long id, Employe employe);

    void supprimer(Long id);
}
