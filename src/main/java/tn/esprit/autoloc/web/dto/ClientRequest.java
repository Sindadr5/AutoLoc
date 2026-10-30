package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Donnees de creation ou de modification d'un client")
public record ClientRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
        @Schema(example = "Ben Ali") String nom,

        @NotBlank(message = "Le prenom est obligatoire")
        @Size(max = 100, message = "Le prenom ne doit pas depasser 100 caracteres")
        @Schema(example = "Sami") String prenom,

        @NotBlank(message = "L'e-mail est obligatoire")
        @Email(message = "L'e-mail n'est pas valide")
        @Schema(example = "sami@mail.tn") String email,

        @Pattern(regexp = "^[0-9]{8}$", message = "Le telephone doit contenir 8 chiffres")
        @Schema(example = "20123456") String telephone,

        @NotBlank(message = "Le numero de permis est obligatoire")
        @Size(max = 30, message = "Le numero de permis ne doit pas depasser 30 caracteres")
        @Schema(example = "P123456") String numPermis,

        @PastOrPresent(message = "La date d'inscription ne peut pas etre dans le futur")
        @Schema(description = "Facultative : date du jour si absente", example = "2026-10-23") LocalDate dateInscription) {
}
