package upc.edu.pe.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.entities.Reservation;
import upc.edu.pe.entities.Hotel;
import upc.edu.pe.exception.ResourceNotFoundException;
import upc.edu.pe.repositories.ReservationRepository;
import upc.edu.pe.repositories.HotelRepository;
import upc.edu.pe.services.ReservationService;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Reservation createReservation(Long hotelId, Reservation reservation) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel","Id", hotelId));

        return reservationRepository.save(reservation.setHotel(hotel));
    }

    @Override
    public List<Reservation> getAllReservationsByHotelId(Long hotelId) {
        return reservationRepository.findReservationsByHotelId(hotelId);
    }

    @Override
    public List<Reservation> findAll() throws Exception {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long aLong) throws Exception {

        return reservationRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Reservation update(Reservation entity, Long aLong) throws Exception {
        Reservation reservation= reservationRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException("Reservation","Id",aLong));
        reservation.setEmployeeId(entity.getEmployeeId())
                .setNumberDays(entity.getNumberDays())
                .setStartDate(entity.getStartDate())
                .setDescription(entity.getDescription());

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Reservation reservation = reservationRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Reservation","Id",aLong));
        reservationRepository.deleteById(aLong);
    }
}
