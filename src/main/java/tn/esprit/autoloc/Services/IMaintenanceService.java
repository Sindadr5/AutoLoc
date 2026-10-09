package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    Maintenance ajouter(Maintenance maintenance);

    List<Maintenance> recupererTous();

    Maintenance recupererParId(Long id);

    Maintenance modifier(Long id, Maintenance maintenance);

    void supprimer(Long id);
}
