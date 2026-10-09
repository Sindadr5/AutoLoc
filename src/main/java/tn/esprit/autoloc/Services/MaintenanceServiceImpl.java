package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    @Transactional
    public Maintenance ajouter(Maintenance maintenance) {
        maintenance.setIdMaintenance(null); // id null : INSERT
        return maintenanceRepository.save(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> recupererTous() {
        return maintenanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Maintenance modifier(Long id, Maintenance maintenance) {
        Maintenance existante = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());
        existante.setVehicule(maintenance.getVehicule());
        return maintenanceRepository.save(existante);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Maintenance introuvable : id=" + id);
        }
        maintenanceRepository.deleteById(id);
    }

    private Maintenance trouverOuErreur(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance introuvable : id=" + id));
    }
}
