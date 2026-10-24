package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import tn.esprit.autoloc.domain.Agence;

@Schema(description = "Agence de location")
public record AgenceDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1") Long id,
        @Schema(example = "Agence Djerba Centre") String nom,
        @Schema(example = "Houmt Souk") String ville,
        @Schema(example = "12 avenue Habib Bourguiba") String adresse,
        @Schema(example = "75123456") String telephone) {

    public static AgenceDto from(Agence agence) {
        return new AgenceDto(agence.getIdAgence(), agence.getNom(), agence.getVille(),
                agence.getAdresse(), agence.getTelephone());
    }

    public Agence toEntity() {
        Agence agence = new Agence();
        agence.setNom(nom);
        agence.setVille(ville);
        agence.setAdresse(adresse);
        agence.setTelephone(telephone);
        return agence;
    }
}
