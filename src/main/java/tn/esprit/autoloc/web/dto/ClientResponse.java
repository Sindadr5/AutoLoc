package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Client retourne par l'API")
public record ClientResponse(Long id, String nom, String prenom, String email, String telephone,
                             String numPermis, LocalDate dateInscription) {
}
