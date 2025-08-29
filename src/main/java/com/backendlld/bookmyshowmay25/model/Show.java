package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "shows")
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    @ManyToOne
    private Theatre theatre;
    private Date time;
    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeats;
    @OneToMany(mappedBy = "show")
    private List<ShowSeatType> showSeatTypes;
    @Enumerated
    private Language language;
    @Enumerated
    @ElementCollection
    private List<Features> features;
}

// Show M : 1 Movie

// Model
// repos : mainly contain functions which will help us interact with databases
// SpringDataJPa

// ORM : Hibernate => SHOW => show table

// Database