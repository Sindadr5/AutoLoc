package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceDto;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Operations CRUD sur les agences")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Creer une agence", description = "Cree une agence. L'identifiant est genere par la base.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie",
                    content = @Content(schema = @Schema(implementation = AgenceDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides ou deja existantes",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDto create(@RequestBody AgenceDto dto) {
        return AgenceDto.from(agenceService.create(dto.toEntity()));
    }

    @Operation(summary = "Lister les agences", description = "Retourne tous les agences.")
    @ApiResponse(responseCode = "200", description = "Liste retournee",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AgenceDto.class))))
    @GetMapping
    public List<AgenceDto> findAll() {
        return agenceService.findAll().stream().map(AgenceDto::from).toList();
    }

    @Operation(summary = "Consulter une agence par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouve",
                    content = @Content(schema = @Schema(implementation = AgenceDto.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public AgenceDto findById(@PathVariable Long id) {
        return AgenceDto.from(agenceService.findById(id));
    }

    @Operation(summary = "Modifier une agence", description = "Remplace les attributs modifiables de une agence existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie",
                    content = @Content(schema = @Schema(implementation = AgenceDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public AgenceDto update(@PathVariable Long id, @RequestBody AgenceDto dto) {
        return AgenceDto.from(agenceService.update(id, dto.toEntity()));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : des donnees y sont liees",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
