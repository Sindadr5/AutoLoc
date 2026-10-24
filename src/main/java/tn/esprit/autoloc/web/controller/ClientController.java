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
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientDto;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operations CRUD sur les clients")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Creer un client", description = "Cree un client. L'identifiant est genere par la base.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides ou deja existantes",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody ClientDto dto) {
        return ClientDto.from(clientService.create(dto.toEntity()));
    }

    @Operation(summary = "Lister les clients", description = "Retourne tous les clients.")
    @ApiResponse(responseCode = "200", description = "Liste retournee",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientDto.class))))
    @GetMapping
    public List<ClientDto> findAll() {
        return clientService.findAll().stream().map(ClientDto::from).toList();
    }

    @Operation(summary = "Consulter un client par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouve",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable Long id) {
        return ClientDto.from(clientService.findById(id));
    }

    @Operation(summary = "Modifier un client", description = "Remplace les attributs modifiables de un client existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant introuvable",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ClientDto update(@PathVariable Long id, @RequestBody ClientDto dto) {
        return ClientDto.from(clientService.update(id, dto.toEntity()));
    }

    @Operation(summary = "Supprimer un client")
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
        clientService.deleteById(id);
    }
}
