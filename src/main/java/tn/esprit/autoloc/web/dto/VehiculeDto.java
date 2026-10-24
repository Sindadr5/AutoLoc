package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

@Schema(description = "Vehicule du parc")
public record VehiculeDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1") Long id,
        @Schema(example = "TN-1234-AB") String immatriculation,
        @Schema(example = "Renault") String marque,
        @Schema(example = "Clio") String modele,
        @Schema(example = "CITADINE") CategorieVehicule categorie,
        @Schema(example = "85.00") BigDecimal tarifJournalier,
        @Schema(example = "DISPONIBLE") StatutVehicule statut,
        @Schema(description = "Identifiant de l'agence (facultatif)", example = "1") Long agenceId) {

    public static VehiculeDto from(Vehicule v) {
        Long idAgence = v.getAgence() == null ? null : v.getAgence().getIdAgence();
        return new VehiculeDto(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele(),
                v.getCategorie(), v.getTarifJournalier(), v.getStatut(), idAgence);
    }

    public Vehicule toEntity(Agence agence) {
        Vehicule v = new Vehicule();
        v.setImmatriculation(immatriculation);
        v.setMarque(marque);
        v.setModele(modele);
        v.setCategorie(categorie);
        v.setTarifJournalier(tarifJournalier);
        v.setStatut(statut);
        v.setAgence(agence);
        return v;
    }
}
