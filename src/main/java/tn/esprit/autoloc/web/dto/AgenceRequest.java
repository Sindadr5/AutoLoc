package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Donnees de creation ou de modification d'une agence")
public record AgenceRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
        @Schema(example = "Agence Djerba Centre") String nom,

        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 100, message = "La ville ne doit pas depasser 100 caracteres")
        @Schema(example = "Houmt Souk") String ville,

        @NotBlank(message = "L'adresse est obligatoire")
        @Size(max = 255, message = "L'adresse ne doit pas depasser 255 caracteres")
        @Schema(example = "12 avenue Habib Bourguiba") String adresse,

        @Pattern(regexp = "^[0-9]{8}$", message = "Le telephone doit contenir 8 chiffres")
        @Schema(example = "75123456") String telephone) {
}
