package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    @Transactional
    public Agence ajouter(Agence agence) {
        agence.setIdAgence(null); // id null : INSERT
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> recupererTous() {
        return agenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Agence recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Agence modifier(Long id, Agence agence) {
        Agence existante = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existante.setNom(agence.getNom());
        existante.setVille(agence.getVille());
        existante.setAdresse(agence.getAdresse());
        existante.setTelephone(agence.getTelephone());
        return agenceRepository.save(existante);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable : id=" + id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence trouverOuErreur(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : id=" + id));
    }
}
