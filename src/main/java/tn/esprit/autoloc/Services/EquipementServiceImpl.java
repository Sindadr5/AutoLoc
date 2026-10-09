package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    @Transactional
    public Equipement ajouter(Equipement equipement) {
        equipement.setIdEquipement(null); // id null : INSERT
        return equipementRepository.save(equipement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> recupererTous() {
        return equipementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Equipement modifier(Long id, Equipement equipement) {
        Equipement existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existant.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipement introuvable : id=" + id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement trouverOuErreur(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipement introuvable : id=" + id));
    }
}
