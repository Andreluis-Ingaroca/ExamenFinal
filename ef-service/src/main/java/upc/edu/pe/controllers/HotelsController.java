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
import upc.edu.pe.entities.Hotel;
import upc.edu.pe.services.ReservationService;
import upc.edu.pe.services.HotelService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/hotels")
public class HotelsController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/{hotelId}")
    public ResponseEntity<Hotel> getTypeById(@PathVariable("hotelId") Long hotelId){
        try {
            Hotel hotel = hotelService.findById(hotelId);
            if (hotel != null){
                return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{hotelId}/reservations")
    public List<Reservation> getAllReservationsByHotelId(@PathVariable("hotelId") Long hotelId){
        List<Reservation> reservations = reservationService.getAllReservationsByHotelId(hotelId);
        return reservations;
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Hotel>> fetchAll(){
        try{
            List<Hotel> hotels = hotelService.findAll();
            return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Hotel hotelDB = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelDB);
    }

    @PutMapping(path = "/{hotelId}")
    public ResponseEntity<?> updateHotel(@PathVariable("hotelId") Long hotelId, @Valid @RequestBody Hotel hotel) throws Exception {
        log.info("Actualizando Hotel con Id {}", hotelId);
        Hotel currentHotel = hotelService.update(hotel, hotelId);
        return ResponseEntity.ok(currentHotel);
    }

    @DeleteMapping(path = "/{hotelId}")
    public void deleteHotel(@PathVariable("hotelId") Long hotelId) throws Exception {
        log.info("Eliminando Hotel con Id {}", hotelId);
        hotelService.deleteById(hotelId);
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
