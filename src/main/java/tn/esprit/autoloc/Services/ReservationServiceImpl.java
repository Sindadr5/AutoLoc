package tn.esprit.autoloc.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    @Transactional
    public Reservation ajouter(Reservation reservation) {
        reservation.setIdReservation(null); // id null : INSERT
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> recupererTous() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation recupererParId(Long id) {
        return trouverOuErreur(id);
    }

    @Override
    @Transactional
    public Reservation modifier(Long id, Reservation reservation) {
        Reservation existante = trouverOuErreur(id); // methode privee : pas d'appel @Transactional via this (S6809)
        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        existante.setVehicule(reservation.getVehicule());
        existante.setClient(reservation.getClient());
        return reservationRepository.save(existante);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation introuvable : id=" + id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation trouverOuErreur(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation introuvable : id=" + id));
    }
}
