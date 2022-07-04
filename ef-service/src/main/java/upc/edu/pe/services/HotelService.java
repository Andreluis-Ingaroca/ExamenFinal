package upc.edu.pe.services;

import upc.edu.pe.entities.Hotel;

public interface HotelService extends CrudService<Hotel,Long> {

    Hotel createHotel(Hotel hotel);

}
