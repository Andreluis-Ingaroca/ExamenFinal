package upc.edu.pe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long employeeId;

    private Integer numberDays;

    private Date startDate;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="hotel_id",nullable = false)
    @JsonIgnore
    private Hotel hotel;

    public Long getId() {
        return id;
    }

    public Reservation setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Reservation setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Integer getNumberDays() {
        return numberDays;
    }

    public Reservation setNumberDays(Integer numberDays) {
        this.numberDays = numberDays;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Reservation setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Reservation setDescription(String description) {
        this.description = description;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Reservation setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }
}
