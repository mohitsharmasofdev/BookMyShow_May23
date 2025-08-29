package com.backendlld.bookmyshowmay25.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User extends BaseModel{
    private String username;
    private String email;
    @OneToMany(mappedBy = "bookedBy")
//    @JoinColumn(name = "booked_by_id")
    private List<Booking> bookings;
}
