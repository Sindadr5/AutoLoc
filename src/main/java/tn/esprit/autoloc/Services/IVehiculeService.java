package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule ajouter(Vehicule vehicule);

    List<Vehicule> recupererTous();

    Vehicule recupererParId(Long id);

    List<Vehicule> recupererParStatut(StatutVehicule statut);

    Vehicule modifier(Long id, Vehicule vehicule);

    void supprimer(Long id);
}
