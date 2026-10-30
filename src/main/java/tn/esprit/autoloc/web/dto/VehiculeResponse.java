package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

@Schema(description = "Vehicule retourne par l'API")
public record VehiculeResponse(Long id, String immatriculation, String marque, String modele,
                               CategorieVehicule categorie, BigDecimal tarifJournalier, StatutVehicule statut,
                               Long agenceId, String agenceNom) {
}
