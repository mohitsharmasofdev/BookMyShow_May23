package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;
import java.util.Date;

@Getter
@Setter
@Entity
public class Payment extends BaseModel {
    private Date date;
    private int amount;
    private String refNumber;
    @Enumerated
    private PaymentStatus status;
    @Enumerated
    private PaymentMode paymentMode;
    @ManyToOne
    private Booking booking;
}
