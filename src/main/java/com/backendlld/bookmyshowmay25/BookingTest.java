package com.backendlld.bookmyshowmay25;

import com.backendlld.bookmyshowmay25.controller.BookingController;
import com.backendlld.bookmyshowmay25.dto.BookTicketRequest;
import com.backendlld.bookmyshowmay25.dto.BookTicketResponse;
import com.backendlld.bookmyshowmay25.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3) // Run after DataInitializer
public class BookingTest implements CommandLineRunner {

    @Autowired
    private BookingController bookingController;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== Testing Booking Functionality ===");
        
        // Wait a bit for data initialization to complete
        Thread.sleep(2000);
        
        // Test booking with custom inputs
        System.out.println("Testing booking with custom inputs...");
        Long userId = 1L;
        Long showId = 1L;
        List<Long> seatIds = List.of(3L, 4L); // Booking 2 seats

        BookTicketRequest request = new BookTicketRequest();;
        request.setUserId(userId);
        request.setShowId(showId);
        request.setShowSeats(seatIds);

        BookTicketResponse response = bookingController.bookTicket(request);
        System.out.println("Booking response: " + response.getMessage());
        
        System.out.println("\n=== Booking Tests Complete ===");
    }
} 