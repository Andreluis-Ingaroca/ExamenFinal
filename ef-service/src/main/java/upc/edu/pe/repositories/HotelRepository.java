package upc.edu.pe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
