package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.Entity;

@Entity
public class ShowSeat extends BaseModel {
    private Show show;
    private Seat seat;
}
