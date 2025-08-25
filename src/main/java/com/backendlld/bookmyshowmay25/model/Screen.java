package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;
    @Enumerated
    @ElementCollection
    private List<Features> screenFeatures;
    @OneToMany
    private List<Seat> seats;
}
// screenrepository.save(screen);
// Feature : Enum

// ManytoMany