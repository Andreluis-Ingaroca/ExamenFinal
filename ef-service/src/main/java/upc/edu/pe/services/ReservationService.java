package upc.edu.pe.services;

import upc.edu.pe.entities.Reservation;

import java.util.List;

public interface ReservationService extends CrudService<Reservation,Long>{

    Reservation createReservation(Long hotelId, Reservation reservation);

    List<Reservation> getAllReservationsByHotelId(Long hotelId);
}
