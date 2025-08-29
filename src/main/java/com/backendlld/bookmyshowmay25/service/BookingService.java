package com.backendlld.bookmyshowmay25.service;


import com.backendlld.bookmyshowmay25.model.*;
import com.backendlld.bookmyshowmay25.repository.BookingRepository;
import com.backendlld.bookmyshowmay25.repository.ShowRepository;
import com.backendlld.bookmyshowmay25.repository.ShowSeatRepository;
import com.backendlld.bookmyshowmay25.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private ShowRepository showRepository;
    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(
            ShowRepository showRepository,
            UserRepository userRepository,
            ShowSeatRepository showSeatRepository,
            BookingRepository bookingRepository
    ) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking bookTicket(
            Long showId,
            Long userId,
            List<Long> showSeatIds
    ){
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] 🎫 Starting booking process for User " + userId + ", Seats: " + showSeatIds);
        
        // 1. get the user from the db
        Optional<User> userOptional =  userRepository.findById(userId);
        User user = null;
        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not Found");
        }
        user = userOptional.get();
        System.out.println("[" + threadName + "] ✅ User validated: " + user.getUsername());

        // 2. Get the show from the db
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            // Create the exception explicitly instead of using Runtime
            throw new RuntimeException("Show Not Found");
        }
        Show show = showOptional.get();
        // 3. Get the show seats from the db
        System.out.println("[" + threadName + "] 🔍 Checking seat availability for seats: " + showSeatIds);
        
        // select * from show_seats where id IN (_ , _ , _) and status = available;
        List<ShowSeat> showSeats = showSeatRepository.findAllByIdAndStatus(showSeatIds , ShowSeatStatus.AVAILABLE);

        if(showSeats.size() < showSeatIds.size()){
            System.out.println("[" + threadName + "] ❌ Seat availability check failed! Found " + showSeats.size() + " available seats out of " + showSeatIds.size() + " requested");
            throw new RuntimeException("Certain Seats are already Booked!");
        }
        System.out.println("[" + threadName + "] ✅ Seat availability check passed! Found " + showSeats.size() + " available seats");
        // 4. Check if all the seats are available
        // 5. If yes, block the seats
        System.out.println("[" + threadName + "] 🔒 Updating seat status to BLOCKED for " + showSeats.size() + " seats");
        for(ShowSeat showSeat : showSeats){
            showSeat.setStatus(ShowSeatStatus.BLOCKED);
        }
        showSeatRepository.saveAll(showSeats);
        System.out.println("[" + threadName + "] ✅ Seats blocked successfully");
        
        // 6. Create the booking
        System.out.println("[" + threadName + "] 📝 Creating booking record");
        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookingDate(new Date());
        booking.setBookedSeats(showSeats);
        booking.setStatus(BookingStatus.PENDING);
        // get the seatTypes
        // Implement logic to get the totalPrice
        booking.setTotalAmount(100);
        booking.setNoOfSeats(showSeats.size());
        Booking savedBooking = bookingRepository.save(booking);
        System.out.println("[" + threadName + "] 🎉 Booking created successfully with ID: " + savedBooking.getId());
        return savedBooking;
    }
}


// HW : Explore Optimisstic locking
// version Number

// showSeats , version
// whenever a write will happen, version will get updated


// showseat 1 1 Booked 3

// Person 1 & 2
