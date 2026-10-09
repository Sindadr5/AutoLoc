package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    @Transactional
    public Employe ajouter(Employe employe) {
        employe.setIdEmploye(null); // id null : INSERT
        return employeRepository.save(employe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> recupererTous() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employe recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Employe modifier(Long id, Employe employe) {
        Employe existant = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        existant.setAgence(employe.getAgence());
        return employeRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable : id=" + id);
        }
        employeRepository.deleteById(id);
    }

    private Employe trouverOuErreur(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe introuvable : id=" + id));
    }
}
