package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.Client;

@Schema(description = "Client de l'agence")
public record ClientDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1") Long id,
        @Schema(example = "Ben Ali") String nom,
        @Schema(example = "Sami") String prenom,
        @Schema(example = "sami@mail.tn") String email,
        @Schema(example = "20123456") String telephone,
        @Schema(example = "P123456") String numPermis,
        @Schema(description = "Facultative : date du jour si absente", example = "2026-10-23") LocalDate dateInscription) {

    public static ClientDto from(Client client) {
        return new ClientDto(client.getIdClient(), client.getNom(), client.getPrenom(), client.getEmail(),
                client.getTelephone(), client.getNumPermis(), client.getDateInscription());
    }

    public Client toEntity() {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setTelephone(telephone);
        client.setNumPermis(numPermis);
        client.setDateInscription(dateInscription);
        return client;
    }
}
