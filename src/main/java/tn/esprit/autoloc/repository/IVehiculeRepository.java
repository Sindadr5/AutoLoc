package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    boolean existsByImmatriculation(String immatriculation);

    List<Vehicule> findByStatut(StatutVehicule statut);
}
