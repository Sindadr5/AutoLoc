package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

public interface IEquipementService {

    Equipement ajouter(Equipement equipement);

    List<Equipement> recupererTous();

    Equipement recupererParId(Long id);

    Equipement modifier(Long id, Equipement equipement);

    void supprimer(Long id);
}
