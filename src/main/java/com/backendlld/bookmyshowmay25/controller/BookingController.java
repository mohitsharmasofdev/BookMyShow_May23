package com.backendlld.bookmyshowmay25.controller;

import com.backendlld.bookmyshowmay25.dto.BookTicketRequest;
import com.backendlld.bookmyshowmay25.dto.BookTicketResponse;
import com.backendlld.bookmyshowmay25.dto.ResponseStatus;
import com.backendlld.bookmyshowmay25.model.Booking;
import com.backendlld.bookmyshowmay25.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookTicketResponse bookTicket(BookTicketRequest request){
        BookTicketResponse response = new BookTicketResponse();
        try {
            Booking booking = bookingService.bookTicket(
                    request.getShowId(),
                    request.getUserId(),
                    request.getShowSeats()
            );
            response.setBookingId(booking.getId());
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Booking Confirmed. Please make the payment!");

        } catch (Exception exception){
            response.setMessage("Booking Failed : " + exception.getMessage());
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}


// BookTicket => Booking