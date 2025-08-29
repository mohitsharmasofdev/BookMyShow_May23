package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    private Date bookingDate;
    private int noOfSeats;
    @ManyToOne
    private User bookedBy;
    private int totalAmount;
    @ManyToMany
    private List<ShowSeat> bookedSeats;
    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}

// Cancellation : One ShowSeat can be present in multiple bookings
