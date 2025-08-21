package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    @ManyToOne
    private Theatre theatre;
    private Date time;
    @OneToMany
    private List<ShowSeat> showSeats;
}

// Show M : 1 Movie

// Model
// repos : mainly contain functions which will help us interact with databases
// SpringDataJPa

// ORM : Hibernate => SHOW => show table

// Database