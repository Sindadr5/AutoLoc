package tn.esprit.autoloc.runner;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.Services.IClientService;
import tn.esprit.autoloc.Services.IVehiculeService;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Demonstration du CRUD complet sur Client et Vehicule.
 * S'execute automatiquement au demarrage de l'application.
 */
@Component
public class CrudDemoRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CrudDemoRunner.class);

    private final IClientService clientService;
    private final IVehiculeService vehiculeService;

    public CrudDemoRunner(IClientService clientService, IVehiculeService vehiculeService) {
        this.clientService = clientService;
        this.vehiculeService = vehiculeService;
    }

    @Override
    public void run(String... args) {
        String suffixe = String.valueOf(System.currentTimeMillis()); // evite les doublons a chaque lancement
        demoClient(suffixe);
        demoVehicule(suffixe);
    }

    private void demoClient(String suffixe) {
        log.info("===== CRUD CLIENT =====");

        // CREATE
        Client client = new Client();
        client.setNom("Ben Ali");
        client.setPrenom("Sami");
        client.setEmail("sami" + suffixe + "@mail.tn");
        client.setTelephone("20123456");
        client.setNumPermis("P" + suffixe);
        client.setDateInscription(LocalDate.now(ZoneId.of("Africa/Tunis")));
        Client cree = clientService.ajouter(client);
        log.info("CREATE : client cree avec id = {}", cree.getIdClient());

        // READ
        Client lu = clientService.recupererParId(cree.getIdClient());
        log.info("READ (par id) : {} {}", lu.getPrenom(), lu.getNom());
        log.info("READ (tous) : {} client(s) en base", clientService.recupererTous().size());

        // UPDATE
        lu.setTelephone("99887766");
        Client modifie = clientService.modifier(lu.getIdClient(), lu);
        log.info("UPDATE : nouveau telephone = {}", modifie.getTelephone());

        // DELETE
        clientService.supprimer(modifie.getIdClient());
        try {
            clientService.recupererParId(modifie.getIdClient());
        } catch (EntityNotFoundException e) {
            log.info("DELETE : client supprime ({})", e.getMessage());
        }
    }

    private void demoVehicule(String suffixe) {
        log.info("===== CRUD VEHICULE =====");

        // CREATE
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TN-" + suffixe);
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setCategorie(CategorieVehicule.values()[0]);
        vehicule.setStatut(StatutVehicule.values()[0]);
        vehicule.setTarifJournalier(new BigDecimal("85.00"));
        Vehicule cree = vehiculeService.ajouter(vehicule);
        log.info("CREATE : vehicule cree avec id = {}", cree.getIdVehicule());

        // READ
        Vehicule lu = vehiculeService.recupererParId(cree.getIdVehicule());
        log.info("READ (par id) : {} {}", lu.getMarque(), lu.getModele());
        log.info("READ (tous) : {} vehicule(s) en base", vehiculeService.recupererTous().size());
        log.info("READ (par statut {}) : {} vehicule(s)", lu.getStatut(),
                vehiculeService.recupererParStatut(lu.getStatut()).size());

        // UPDATE
        lu.setTarifJournalier(new BigDecimal("95.00"));
        Vehicule modifie = vehiculeService.modifier(lu.getIdVehicule(), lu);
        log.info("UPDATE : nouveau tarif = {}", modifie.getTarifJournalier());

        // DELETE
        vehiculeService.supprimer(modifie.getIdVehicule());
        try {
            vehiculeService.recupererParId(modifie.getIdVehicule());
        } catch (EntityNotFoundException e) {
            log.info("DELETE : vehicule supprime ({})", e.getMessage());
        }
    }
}
