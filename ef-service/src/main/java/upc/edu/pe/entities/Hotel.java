package upc.edu.pe.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="hotels")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String department;

    private String province;

    private String district;

    public Long getId() {
        return id;
    }

    public Hotel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hotel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Hotel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public Hotel setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public Hotel setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public Hotel setDistrict(String district) {
        this.district = district;
        return this;
    }
}
