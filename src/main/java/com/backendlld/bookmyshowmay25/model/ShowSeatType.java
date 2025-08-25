package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    private SeatType seatType;
    private int price;
}

// 1 Gold 200
// 1 Silver 150
// 1 Bronze 100

// 2 Gold 200
// 2 Silver 150
// 2 Bronze 100