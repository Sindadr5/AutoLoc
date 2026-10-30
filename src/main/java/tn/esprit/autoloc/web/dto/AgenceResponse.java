package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Agence retournee par l'API")
public record AgenceResponse(Long id, String nom, String ville, String adresse, String telephone) {
}
