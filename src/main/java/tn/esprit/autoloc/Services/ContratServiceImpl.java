package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    @Transactional
    public Contrat ajouter(Contrat contrat) {
        if (contrat.getPaiements() != null) {
            contrat.getPaiements().forEach(p -> p.setContrat(contrat)); // maintient le cote proprietaire de l'association
        }
        contrat.setIdContrat(null); // id null : INSERT
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> recupererTous() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Contrat modifier(Long id, Contrat contrat) {
        Contrat existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        existant.setReservation(contrat.getReservation());
        return contratRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable : id=" + id);
        }
        contratRepository.deleteById(id);
    }

    private Contrat trouverOuErreur(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable : id=" + id));
    }
}
