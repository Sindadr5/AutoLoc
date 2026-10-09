package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    @Transactional
    public Paiement ajouter(Paiement paiement) {
        paiement.setIdPaiement(null); // id null : INSERT
        return paiementRepository.save(paiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paiement> recupererTous() {
        return paiementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Paiement recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Paiement modifier(Long id, Paiement paiement) {
        Paiement existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existant.setMontant(paiement.getMontant());
        existant.setDatePaiement(paiement.getDatePaiement());
        existant.setModePaiement(paiement.getModePaiement());
        existant.setContrat(paiement.getContrat());
        return paiementRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new EntityNotFoundException("Paiement introuvable : id=" + id);
        }
        paiementRepository.deleteById(id);
    }

    private Paiement trouverOuErreur(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement introuvable : id=" + id));
    }
}
