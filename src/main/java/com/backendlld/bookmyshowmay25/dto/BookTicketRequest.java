package com.backendlld.bookmyshowmay25.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketRequest {
    private Long showId;
    private Long userId;
    private List<Long> showSeats;
}


// Theatre Seat Show
// UserId, Show, Seat


// Seat Layout , selected some seats of a particular show


// /getShowSeats?id = 7
// showSeatIds = {}, user_id , show_id