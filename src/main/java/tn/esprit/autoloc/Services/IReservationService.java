package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

public interface IReservationService {

    Reservation ajouter(Reservation reservation);

    List<Reservation> recupererTous();

    Reservation recupererParId(Long id);

    Reservation modifier(Long id, Reservation reservation);

    void supprimer(Long id);
}
