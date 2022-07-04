package upc.edu.pe.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upc.edu.pe.entities.Reservation;
import upc.edu.pe.services.ReservationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/hotels")
public class ReservationsController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/reservations/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("reservationId") Long reservationId){
        try {
            Reservation reservation = reservationService.findById(reservationId);
            if (reservation != null){
                return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/reservations", produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Reservation>> fetchAll(){
        try{
            List<Reservation> reservations = reservationService.findAll();
            return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{hotelId}/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation, @PathVariable("hotelId") Long hotelId, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Reservation reservationDB = reservationService.createReservation(hotelId,reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationDB);
    }

    @PutMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable("reservationId") Long reservationId, @Valid @RequestBody Reservation reservation) throws Exception {
        log.info("Actualizando Reservation con Id {}", reservationId);
        Reservation currentReservation = reservationService.update(reservation, reservationId);
        return ResponseEntity.ok(currentReservation);
    }

    @DeleteMapping(path = "/reservations/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) throws Exception {
        log.info("Eliminando Reservation con Id {}", reservationId);
        reservationService.deleteById(reservationId);
    }

    private String formatMessage( BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
