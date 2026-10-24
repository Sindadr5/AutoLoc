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
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDto;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Operations CRUD sur les vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final IAgenceService agenceService;

    @Operation(summary = "Creer un vehicule", description = "Cree un vehicule. L'identifiant est genere par la base.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie",
                    content = @Content(schema = @Schema(implementation = VehiculeDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides ou deja existantes",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@RequestBody VehiculeDto dto) {
        return VehiculeDto.from(vehiculeService.create(dto.toEntity(chargerAgence(dto.agenceId()))));
    }

    @Operation(summary = "Lister les vehicules", description = "Retourne tous les vehicules.")
    @ApiResponse(responseCode = "200", description = "Liste retournee",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculeDto.class))))
    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAll().stream().map(VehiculeDto::from).toList();
    }

    @Operation(summary = "Consulter un vehicule par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouve",
                    content = @Content(schema = @Schema(implementation = VehiculeDto.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public VehiculeDto findById(@PathVariable Long id) {
        return VehiculeDto.from(vehiculeService.findById(id));
    }

    @Operation(summary = "Modifier un vehicule", description = "Remplace les attributs modifiables de un vehicule existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie",
                    content = @Content(schema = @Schema(implementation = VehiculeDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public VehiculeDto update(@PathVariable Long id, @RequestBody VehiculeDto dto) {
        return VehiculeDto.from(vehiculeService.update(id, dto.toEntity(chargerAgence(dto.agenceId()))));
    }

    @Operation(summary = "Supprimer un vehicule")
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
        vehiculeService.deleteById(id);
    }

    private Agence chargerAgence(Long agenceId) {
        return agenceId == null ? null : agenceService.findById(agenceId); // 404 si l'agence n'existe pas
    }
}
