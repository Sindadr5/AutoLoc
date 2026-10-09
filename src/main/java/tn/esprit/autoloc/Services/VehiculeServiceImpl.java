package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Vehicule ajouter(Vehicule vehicule) {
        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException("Immatriculation deja utilisee : " + vehicule.getImmatriculation());
        }
        vehicule.setIdVehicule(null); // id null : INSERT
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> recupererTous() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> recupererParStatut(StatutVehicule statut) {
        return vehiculeRepository.findByStatut(statut);
    }

    @Override
    @Transactional
    public Vehicule modifier(Long id, Vehicule vehicule) {
        Vehicule existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        if (!existant.getImmatriculation().equals(vehicule.getImmatriculation())
                && vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException("Immatriculation deja utilisee : " + vehicule.getImmatriculation());
        }
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());
        return vehiculeRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicule introuvable : id=" + id);
        }
        vehiculeRepository.deleteById(id);
    }

    private Vehicule trouverOuErreur(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule introuvable : id=" + id));
    }
}
