package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

@Schema(description = "Donnees de creation ou de modification d'un vehicule")
public record VehiculeRequest(
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas depasser 20 caracteres")
        @Schema(example = "TN-1234-AB") String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas depasser 50 caracteres")
        @Schema(example = "Renault") String marque,

        @NotBlank(message = "Le modele est obligatoire")
        @Size(max = 50, message = "Le modele ne doit pas depasser 50 caracteres")
        @Schema(example = "Clio") String modele,

        @NotNull(message = "La categorie est obligatoire")
        @Schema(example = "CITADINE") CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le tarif journalier doit etre strictement positif")
        @Schema(example = "85.00") BigDecimal tarifJournalier,

        @NotNull(message = "Le statut est obligatoire")
        @Schema(example = "DISPONIBLE") StatutVehicule statut,

        @Positive(message = "L'identifiant de l'agence doit etre positif")
        @Schema(description = "Identifiant de l'agence (facultatif)", example = "1") Long agenceId) {
}
