package upc.edu.pe.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.entities.Hotel;
import upc.edu.pe.exception.ResourceNotFoundException;
import upc.edu.pe.repositories.HotelRepository;
import upc.edu.pe.services.HotelService;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> findAll() throws Exception {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(Long aLong) throws Exception {
        return hotelRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Hotel update(Hotel entity, Long aLong) throws Exception {
        Hotel hotel = hotelRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
        hotel.setName(entity.getName())
                .setDescription(entity.getName())
                .setDepartment(entity.getDepartment())
                .setProvince(entity.getProvince())
                .setDistrict(entity.getDistrict());
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Hotel hotel = hotelRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
        hotelRepository.deleteById(aLong);
    }
}
