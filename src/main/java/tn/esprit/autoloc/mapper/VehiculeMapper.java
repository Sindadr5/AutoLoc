package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequest request, Agence agence) {
        Vehicule vehicule = new Vehicule();
        updateEntity(request, agence, vehicule);
        return vehicule;
    }

    public void updateEntity(VehiculeRequest request, Agence agence, Vehicule vehicule) {
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
        vehicule.setAgence(agence);
    }

    public VehiculeResponse toResponse(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        return new VehiculeResponse(vehicule.getIdVehicule(), vehicule.getImmatriculation(), vehicule.getMarque(),
                vehicule.getModele(), vehicule.getCategorie(), vehicule.getTarifJournalier(), vehicule.getStatut(),
                agence == null ? null : agence.getIdAgence(),
                agence == null ? null : agence.getNom());
    }
}
